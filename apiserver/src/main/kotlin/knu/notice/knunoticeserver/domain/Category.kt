package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "category")
class Category(
    @Id
    val code: String,
    @JsonProperty("name")
    val department: String,
    @JsonProperty("api_url")
    val apiUrl: String
) {
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    val subscriptions: List<Subscription> = Collections.emptyList()
}
