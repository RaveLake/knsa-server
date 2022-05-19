package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Subscription
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository : JpaRepository<Subscription, Long> {
    @EntityGraph(attributePaths = ["device"])
    fun getAllByCategoryEquals(category: String): List<Subscription>
}