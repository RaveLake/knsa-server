package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "push_status")
class PushStatus(
    @Id
    val id: Long,
    @JsonProperty("notice_id")
    val noticeId: Long,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime
)