package knu.notice.knunoticeserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import knu.notice.knunoticeserver.domain.MessageInfo
import knu.notice.knunoticeserver.domain.PushStatus
import knu.notice.knunoticeserver.repository.CategoryRepository
import knu.notice.knunoticeserver.repository.NoticeRepository
import knu.notice.knunoticeserver.repository.PushStatusRepository
import knu.notice.knunoticeserver.util.getLogger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class PushAlarmService(
    private val noticeRepository: NoticeRepository,
    private val pushStatusRepository: PushStatusRepository,
    private val categoryRepository: CategoryRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    companion object {
        val logger = getLogger()
        val objectMapper = ObjectMapper()
        const val TOPIC = "alarm"
        const val PushStatusId = "LastId"
        const val keywordAlarmTitle = "설정된 키워드를 가진 공지가 올라왔어요!"
        const val subscriptionAlarmTitle = "새 공지가 올라왔어요!"
    }

    @Async
    fun sendMessage() {
        val categoryList = categoryRepository.findAll()
        val mappingName = hashMapOf<String, String>()
        // 학과 code와 이름을 매핑하기 위한 HashMap
        for (category in categoryList) {
            mappingName[category.code] = category.department
        }
        // PushStatus에서 마지막으로 보낸 공지사항의 번호를 가져온다.
        val prevStatus = pushStatusRepository.getById(PushStatusId)
        // 이후에 새로 들어온 모든 공지를 가져온다.
        val newNoticeList = noticeRepository.getAllByIdGreaterThanOrderByIdAsc(prevStatus.noticeId)
        if(newNoticeList.isEmpty())
            return
        val lastNoticeId = newNoticeList[newNoticeList.size - 1].id

        for (notice in newNoticeList) {
            loop@ for (user in notice.code.subscriptions) {
                val code = user.category.code
                val userDevice = user.device
                val deviceId = userDevice.id
                if(userDevice.alarmSwitchKey) {
                    for (keyword in user.device.keywords) {
                        if (notice.title.contains(keyword.keyword)) {
                            val message =
                                MessageInfo(keywordAlarmTitle, mappingName[code]!! + ": " + keyword.keyword, deviceId)
                            kafkaTemplate.send(TOPIC, getJsonStringMessageInfo(message))
                            continue@loop
                        }
                    }
                }
                if(userDevice.alarmSwitchSub) {
                    val message = MessageInfo(subscriptionAlarmTitle, mappingName[code]!!, deviceId)
                    kafkaTemplate.send(TOPIC, getJsonStringMessageInfo(message))
                }
            }
        }

        // 가장 마지막 공지를 PushStatus에 다시 저장한다.
        pushStatusRepository.save(PushStatus(PushStatusId, lastNoticeId))
    }

    fun getJsonStringMessageInfo(message: MessageInfo): String {
        return objectMapper.writeValueAsString(message)
    }
}