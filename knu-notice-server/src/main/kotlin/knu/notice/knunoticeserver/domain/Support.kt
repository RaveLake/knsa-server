package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "support")
data class Support(
    @Id
    val id: Int,
    val latest: String,
    @JsonProperty("available_version_code")
    val availableVersionCode: Int
)