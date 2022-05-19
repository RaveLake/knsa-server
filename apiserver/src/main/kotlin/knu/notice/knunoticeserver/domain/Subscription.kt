package knu.notice.knunoticeserver.domain

import javax.persistence.*

@Entity
@Table(name = "subscription")
class Subscription(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    val device: Device,
    val category: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}