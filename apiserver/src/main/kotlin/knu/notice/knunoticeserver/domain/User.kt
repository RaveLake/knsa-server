package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.enums.Permission
import knu.notice.knunoticeserver.dto.UserDTO
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    val id: String,
    val password: String?,
    @JsonProperty("last_login")
    var lastLogin: LocalDateTime?,
    var email: String?,
    @JsonProperty("synched_at")
    val synchedAt: LocalDateTime?,
    @JsonProperty("connected_at")
    var connectedAt: LocalDateTime?,
    @JsonProperty("is_active")
    var isActive: Boolean = false,
    @Enumerated(EnumType.STRING)
    var role: Permission = Permission.User,
    @JsonProperty("device_id")
    var deviceId: String
) {
    constructor(user: UserDTO, now: LocalDateTime) : this(
        user.id,
        null,
        null,
        user.email,
        now,
        now,
        true,
        Permission.User,
        user.deviceId
    )

    fun update(updateUser: UserDTO) {
        email = updateUser.email
        deviceId = updateUser.deviceId
        connectedAt = LocalDateTime.now()
    }
}