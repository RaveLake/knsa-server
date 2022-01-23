package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "category")
class Category(@Id val id: Long, val department: String, @JsonProperty("api_url") val apiUrl: String, val code: String)
