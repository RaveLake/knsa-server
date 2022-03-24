package knu.notice.knunoticeserver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.Device

class DeviceDTO(
    val id: String,
    @JsonProperty("id_method")
    val idMethod: String,
    val keywords: List<String>,
    val subscriptions: List<String>,
    @JsonProperty("alarm_switch_sub")
    val alarmSwitchSub: Boolean = false,
    @JsonProperty("alarm_switch_key")
    val alarmSwitchKey: Boolean = false
) {
    constructor(device: Device, keywords: List<String>, subscriptions: List<String>) : this(
        device.id,
        device.idMethod,
        keywords,
        subscriptions,
        device.alarmSwitchSub,
        device.alarmSwitchKey
    )
}