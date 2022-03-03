package knu.notice.knunoticeserver.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import knu.notice.knunoticeserver.domain.DefaultPage
import knu.notice.knunoticeserver.domain.Notice
import knu.notice.knunoticeserver.domain.QNotice
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

@Repository
class NoticeCustomRepository(private val jpaQueryFactory: JPAQueryFactory) {

    fun getTotalSizeByDepartmentAndKeyword(departments: List<String>, keywords: List<String>): Long {
        val qNotice = QNotice.notice
        return jpaQueryFactory
            .selectFrom(qNotice)
            .where(getDepartmentsAndKeywordsWhere(departments, keywords))
            .orderBy(qNotice.createdAt.desc())
            .fetch().size.toLong()
    }

    fun getAllByDepartmentAndKeyword(
        departments: List<String>,
        keywords: List<String>,
        curPageNumber: Int
    ): List<Notice> {
        val qNotice = QNotice.notice
        return jpaQueryFactory
            .selectFrom(qNotice)
            .where(getDepartmentsAndKeywordsWhere(departments, keywords))
            .orderBy(qNotice.createdAt.desc())
            .offset(curPageNumber.toLong())
            .limit(DefaultPage.toLong())
            .fetch()
    }

    private fun getDepartmentsAndKeywordsWhere(departments: List<String>, keywords: List<String>): BooleanExpression {
        val qNotice = QNotice.notice
        val boolExp = BooleanBuilder()
        for (keyword in keywords) {
            boolExp.or(qNotice.title.like("%$keyword%"))
        }
        return qNotice.code.`in`(departments).and(boolExp)
    }
}