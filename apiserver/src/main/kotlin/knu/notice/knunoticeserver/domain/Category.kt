package knu.notice.knunoticeserver.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

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
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    val subscriptions: List<Subscription> = Collections.emptyList()
}
