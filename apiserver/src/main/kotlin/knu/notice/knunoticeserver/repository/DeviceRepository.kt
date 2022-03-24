package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<Device, String>