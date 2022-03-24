package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.dto.DeviceDTO
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "device")
class Device(
    @Id
    val id: String,
    @JsonProperty("id_method")
    var idMethod: String,
    @JsonProperty("alarm_switch_sub")
    var alarmSwitchSub: Boolean,
    @JsonProperty("alarm_switch_key")
    var alarmSwitchKey: Boolean,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime,
    @JsonProperty("updated_at")
    var updatedAt: LocalDateTime
) {
    constructor(device: DeviceDTO, now: LocalDateTime) : this(
        device.id,
        device.idMethod,
        device.alarmSwitchSub,
        device.alarmSwitchKey,
        now, now
    )

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    var keywords: List<Keyword> = Collections.emptyList()

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    var subscriptions: List<Subscription> = Collections.emptyList()

    fun update(updateDevice: DeviceDTO) {
        idMethod = updateDevice.idMethod
        alarmSwitchSub = updateDevice.alarmSwitchSub
        alarmSwitchKey = updateDevice.alarmSwitchKey
        updatedAt = LocalDateTime.now()
    }
}