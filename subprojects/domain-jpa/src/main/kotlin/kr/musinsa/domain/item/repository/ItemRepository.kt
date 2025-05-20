package kr.musinsa.domain.item.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.musinsa.domain.item.model.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<ItemEntity, Long>, UserCustomRepository

interface UserCustomRepository

open class UserCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): UserCustomRepository
