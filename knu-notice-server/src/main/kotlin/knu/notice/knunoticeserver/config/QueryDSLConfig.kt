package knu.notice.knunoticeserver.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDSLConfig(@PersistenceContext private val entityManager: EntityManager) {
    @Bean
    fun jpaQueryDSLFactory() = JPAQueryFactory(this.entityManager)
}