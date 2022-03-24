package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Subscription
import org.springframework.data.jpa.repository.JpaRepository

interface Subscriptionrepository : JpaRepository<Subscription, Long>