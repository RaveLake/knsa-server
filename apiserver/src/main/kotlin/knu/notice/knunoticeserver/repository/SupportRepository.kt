package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Support
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupportRepository : JpaRepository<Support, String>