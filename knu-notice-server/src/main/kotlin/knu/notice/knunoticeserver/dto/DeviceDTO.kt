package knu.notice.knunoticeserver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.Device

class DeviceDTO(
    val id: String,
    @JsonProperty("id_method")
    val idMethod: String,
    val keywords: String,
    val subscriptions: String,
    @JsonProperty("alarm_switch_sub")
    val alarmSwitchSub: Boolean = false,
    @JsonProperty("alarm_switch_key")
    val alarmSwitchKey: Boolean = false
) {
    constructor(device: Device) : this(
        device.id,
        device.idMethod,
        device.keywords,
        device.subscriptions,
        device.alarmSwitchSub,
        device.alarmSwitchKey
    )
}