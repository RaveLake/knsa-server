package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : JpaRepository<Notice, Long> {
    fun getAllByCodeInOrderByCreatedAtDesc(codes: List<String>, page: Pageable): Page<Notice>
    fun getAllByIdGreaterThan(prevId: Long): List<Notice>
}