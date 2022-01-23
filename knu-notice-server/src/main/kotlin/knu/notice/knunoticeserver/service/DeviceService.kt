package knu.notice.knunoticeserver.service

import knu.notice.knunoticeserver.domain.Device
import knu.notice.knunoticeserver.error.exception.BadRequestException
import knu.notice.knunoticeserver.error.exception.DeviceNotFoundException
import knu.notice.knunoticeserver.dto.DeviceDTO
import knu.notice.knunoticeserver.repository.DeviceRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class DeviceService(private val deviceRepository: DeviceRepository) {

    fun getDeviceById(id: String): DeviceDTO {
        return try {
            DeviceDTO(deviceRepository.getById(id))
        } catch (e: JpaObjectRetrievalFailureException) {
            throw DeviceNotFoundException()
        } catch (e: Exception) {
            throw BadRequestException()
        }
    }

    @Transactional
    fun saveNewDevice(newDevice: DeviceDTO): DeviceDTO {
        if (deviceRepository.findById(newDevice.id).isPresent) {
            throw BadRequestException()
        } else {
            val result = deviceRepository.save(Device(newDevice, LocalDateTime.now()))
            return DeviceDTO(result)
        }
    }

    @Transactional
    fun updateDevice(updateDevice: DeviceDTO): DeviceDTO {
        getDeviceById(updateDevice.id)
        val existingUser = deviceRepository.getById(updateDevice.id)
        existingUser.update(updateDevice)
        val result = deviceRepository.save(existingUser)
        return DeviceDTO(result)
    }

    @Transactional
    fun deleteDeviceById(id: String) {
        getDeviceById(id)
        deviceRepository.deleteById(id)
    }
}