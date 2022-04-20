package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "notice")
class Notice(
    @Id
    val id: Long,
    val bid: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    val code: Category,
    @JsonProperty("is_fixed")
    val isFixed: Boolean,
    val title: String,
    val link: String,
    val date: LocalDate,
    val author: String?,
    val reference: String?,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
)