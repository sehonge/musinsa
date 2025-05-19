package kr.musinsa.domain.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslConfig(
    @PersistenceContext(unitName = "entityManager")
    private val entityManager: EntityManager,
) {
    @Bean("jpaQueryFactory")
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}
