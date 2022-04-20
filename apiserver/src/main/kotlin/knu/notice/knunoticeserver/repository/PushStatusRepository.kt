package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.PushStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PushStatusRepository : JpaRepository<PushStatus, String>