package knu.notice.knunoticeserver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.Notice
import java.time.LocalDate
import java.time.LocalDateTime

class NoticeDTO(
    val id: String,
    val title: String,
    val link: String,
    val date: LocalDate,
    val author: String?,
    val reference: String?,
    @JsonProperty("is_fixed")
    val isFixed: Boolean,
    val created_at: LocalDateTime
) {
    constructor(notice: Notice) : this(
        notice.id.toString(),
        notice.title,
        notice.link,
        notice.date,
        notice.author,
        notice.reference,
        notice.isFixed,
        notice.createdAt
    )
}