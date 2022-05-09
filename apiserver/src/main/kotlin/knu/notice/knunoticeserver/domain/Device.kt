package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.dto.DeviceDTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
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
) {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    var updatedAt: LocalDateTime= LocalDateTime.MIN
        private set

    constructor(device: DeviceDTO) : this(
        device.id,
        device.idMethod,
        device.alarmSwitchSub,
        device.alarmSwitchKey,
    )

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    @JsonIgnore
    var keywords: List<Keyword> = Collections.emptyList()

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    @JsonIgnore
    var subscriptions: List<Subscription> = Collections.emptyList()

    fun update(updateDevice: DeviceDTO) {
        idMethod = updateDevice.idMethod
        alarmSwitchSub = updateDevice.alarmSwitchSub
        alarmSwitchKey = updateDevice.alarmSwitchKey
    }
}