package knu.notice.knunoticeserver.domain

import javax.persistence.*

@Entity
@Table(name = "keyword")
class Keyword(
    @ManyToOne
    @JoinColumn(name = "device_id")
    val device: Device,
    val keyword: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}