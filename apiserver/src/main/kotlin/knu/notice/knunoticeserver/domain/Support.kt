package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "support")
data class Support(
    @Id
    @JsonIgnore
    val id: String,
    val latest: String,
    @JsonProperty("available_version_code")
    val availableVersionCode: Int
)