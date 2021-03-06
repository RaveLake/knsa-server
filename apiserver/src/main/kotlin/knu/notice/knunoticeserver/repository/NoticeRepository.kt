package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : JpaRepository<Notice, Long> {
    @Query("SELECT COUNT(n) FROM Notice n WHERE n.code.code in(:codes)")
    fun getTotalSizeByCodeIn(codes: List<String>): Long

    @Query("SELECT n FROM Notice n WHERE n.code.code in (:codes) ORDER BY n.date DESC")
    fun getAllByCodeInOrderByDateDesc(codes: List<String>, page: Pageable): Page<Notice>
    fun getAllByIdGreaterThanOrderByIdAsc(prevId: Long): List<Notice>
}