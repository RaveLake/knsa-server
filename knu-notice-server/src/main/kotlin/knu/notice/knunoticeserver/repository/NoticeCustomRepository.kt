package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Notice
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

@Repository
class NoticeCustomRepository(private val jdbcTemplate: JdbcTemplate) {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0")
        val rowMapper = RowMapper<Notice> { rs: ResultSet, _: Int ->
            Notice(
                rs.getLong("id"),
                rs.getLong("bid"),
                rs.getString("code"),
                rs.getInt("is_fixed") == 1,
                rs.getString("title"),
                rs.getString("link"),
                rs.getDate("date").toLocalDate(),
                rs.getString("author"),
                rs.getString("reference"),
                LocalDateTime.parse(rs.getString("created_at"), formatter)
            )
        }
    }

    fun getAllByDepartmentAndKeyword(departments: List<String>, keywords: List<String>): List<Notice> {
        val query = getQuery(departments, keywords)
        return jdbcTemplate.query(query, rowMapper)
    }

    private fun getQuery(departments: List<String>, keywords: List<String>): String {
        return "SELECT id, bid, code, is_fixed, title, link, date, author, reference, created_at " +
                "FROM notice WHERE (code in ('" + departments.stream().collect(Collectors.joining("', '")) + "')) " +
                "AND (title like '%" + keywords.stream().collect(Collectors.joining("%' or title like '%")) + "%') " +
                "ORDER BY created_at desc"
    }
}