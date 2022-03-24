package knu.notice.knunoticeserver.domain

import javax.persistence.*

@Entity
@Table(name = "subscription")
class Subscription(
    @ManyToOne
    @JoinColumn(name = "device_id")
    val device: Device,
    @ManyToOne
    @JoinColumn(name = "category_code")
    val category: Category
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}