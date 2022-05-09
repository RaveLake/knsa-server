package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.enums.Permission
import knu.notice.knunoticeserver.dto.UserDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "user")
class User(
    @Id
    val id: String,
    val password: String?,
    @JsonProperty("last_login")
    var lastLogin: LocalDateTime?,
    var email: String?,
    @JsonProperty("is_active")
    var isActive: Boolean = false,
    @Enumerated(EnumType.STRING)
    var role: Permission = Permission.User,
    @JsonProperty("device_id")
    var deviceId: String
) {
    @CreatedDate
    @JsonProperty("synched_at")
    var synchedAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    @JsonProperty("connected_at")
    var connectedAt: LocalDateTime = LocalDateTime.MIN
        private set

    constructor(user: UserDTO) : this(
        user.id,
        null,
        null,
        user.email,
        true,
        Permission.User,
        user.deviceId
    )

    fun update(updateUser: UserDTO) {
        email = updateUser.email
        deviceId = updateUser.deviceId
    }
}