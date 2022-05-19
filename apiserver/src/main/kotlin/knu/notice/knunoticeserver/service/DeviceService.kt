package knu.notice.knunoticeserver.service

import knu.notice.knunoticeserver.domain.Device
import knu.notice.knunoticeserver.domain.Keyword
import knu.notice.knunoticeserver.domain.Subscription
import knu.notice.knunoticeserver.dto.DeviceDTO
import knu.notice.knunoticeserver.error.exception.DeviceNotFoundException
import knu.notice.knunoticeserver.repository.CategoryRepository
import knu.notice.knunoticeserver.repository.DeviceRepository
import knu.notice.knunoticeserver.repository.KeywordRepository
import knu.notice.knunoticeserver.repository.SubscriptionRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import javax.transaction.Transactional
import kotlin.streams.toList

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
    private val categoryRepository: CategoryRepository,
    private val keywordRepository: KeywordRepository,
    private val subscriptionRepository: SubscriptionRepository
) {

    fun getDeviceById(id: String): DeviceDTO {
        return try {
            val device = deviceRepository.getById(id)
            val keywords = device.keywords.stream().map { v -> v.keyword }.collect(Collectors.toList())
            val subscriptions =
                device.subscriptions.stream().map { v -> v.category }.collect(Collectors.toList())
            DeviceDTO(deviceRepository.getById(id), keywords, subscriptions)
        } catch (e: JpaObjectRetrievalFailureException) {
            throw DeviceNotFoundException()
        }
    }

    @Transactional
    fun saveNewDevice(newDevice: DeviceDTO): DeviceDTO {
        if (deviceRepository.findById(newDevice.id).isPresent) {
            throw DeviceNotFoundException()
        }
        val result = deviceRepository.save(Device(newDevice))
        return DeviceDTO(result, Collections.emptyList(), Collections.emptyList())
    }

    /**
     * 사용자가 기존에 사용하던 keyword를 가져와 hash로 저장한다.
     * 새로 들어온 기기 정보의 keyword와 비교하여 이미 있는 정보일 경우 hash에서 제거한다.
     * 없는 정보일 경우 addKeywordList에 추가한다.
     * hash에 남은 데이터는 지워야 하는 데이터이고 addKeywordList에 있는 데이터는 추가해주어야 하는 데이터이다.
     *
     * subscription도 위와 같은 방법으로 수정해준다.
     */
    @Transactional
    fun updateDevice(updateDevice: DeviceDTO): DeviceDTO {
        val device = deviceRepository.findById(updateDevice.id)
        if (device.isEmpty) {
            throw DeviceNotFoundException()
        }
        val existingDevice = device.get()
        existingDevice.update(updateDevice)
        val keywordHash = existingDevice.keywords.associateBy { it.keyword }.toMutableMap()
        val addKeywordList = arrayListOf<Keyword>()
        for (keyword in updateDevice.keywords) {
            if (keywordHash.containsKey(keyword)) {
                keywordHash.remove(keyword)
            } else {
                addKeywordList.add(Keyword(existingDevice, keyword))
            }
        }

        val categorySet = categoryRepository.findAll().stream().map { it.code }.toList().toHashSet()
        val subscriptionHash = existingDevice.subscriptions.associateBy { it.category }.toMutableMap()
        val addSubscriptionList = arrayListOf<Subscription>()
        for (subscription in updateDevice.subscriptions) {
            if (subscriptionHash.containsKey(subscription)) {
                subscriptionHash.remove(subscription)
            } else if(categorySet.contains(subscription)) {
                addSubscriptionList.add(Subscription(existingDevice, subscription))
            }
        }

        keywordRepository.deleteAllByIdInBatch(keywordHash.values.stream().map { v -> v.id }
            .collect(Collectors.toList()))
        keywordRepository.saveAll(addKeywordList)
        subscriptionRepository.deleteAllByIdInBatch(subscriptionHash.values.stream().map { v -> v.id }
            .collect(Collectors.toList()))
        subscriptionRepository.saveAll(addSubscriptionList)
        return updateDevice
    }

    @Transactional
    fun deleteDeviceById(id: String) {
        val device = deviceRepository.findById(id)
        if (device.isEmpty) {
            throw DeviceNotFoundException()
        }
        val removeDevice = device.get()
        val keywordIdList = removeDevice.keywords.stream().map { v -> v.id?.let { it } }.toList()
        val subscriptionIdList = removeDevice.subscriptions.stream().map { v -> v.id?.let { it } }.toList()
        keywordRepository.deleteAllByIdInBatch(keywordIdList)
        subscriptionRepository.deleteAllByIdInBatch(subscriptionIdList)
        deviceRepository.deleteById(id)
    }
}