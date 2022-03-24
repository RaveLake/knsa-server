package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<Device, String> {
    @Query("SELECT d FROM Device d WHERE d.subscriptions like %:department%")
    fun getAllBySubscriptionsLike(department: String): List<Device>
}