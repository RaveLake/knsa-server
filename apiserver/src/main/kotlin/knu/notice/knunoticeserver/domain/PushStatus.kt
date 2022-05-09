package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.Table

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "push_status")
class PushStatus(
    @Id
    val id: String,
    @JsonProperty("notice_id")
    val noticeId: Long,
) {
    @LastModifiedDate
    @JsonProperty("updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.MIN
        private set
}