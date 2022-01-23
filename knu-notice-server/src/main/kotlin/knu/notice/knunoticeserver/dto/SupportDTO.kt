package knu.notice.knunoticeserver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import knu.notice.knunoticeserver.domain.Support

class SupportDTO(val latest: String, @JsonProperty("available_version_code") val availableVersionCode: Int) {
    constructor(support: Support) : this(support.latest, support.availableVersionCode)
}