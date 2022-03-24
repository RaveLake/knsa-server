package knu.notice.knunoticeserver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.User

class UserDTO(val id: String, val email: String?, @JsonProperty("device_id") val deviceId: String) {
    constructor(user: User) : this(user.id, user.email, user.deviceId)
}