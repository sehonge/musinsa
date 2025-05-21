package kr.musinsa.domain.item.repository

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.musinsa.domain.item.model.ItemEntity
import kr.musinsa.domain.item.model.QItemEntity
import kr.musinsa.domain.item.model.dto.MinPriceItemProjection
import kr.musinsa.domain.item.model.dto.QMinPriceItemProjection
import kr.musinsa.domain.item.model.enums.ItemCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<ItemEntity, Long>, UserCustomRepository

interface UserCustomRepository {
    fun findItem(brand: String, category: ItemCategory): ItemEntity?
    fun findItemById(id: Long): ItemEntity?
    fun findMinPriceByCategory(category: ItemCategory): ItemEntity?
    fun findMaxPriceByCategory(category: ItemCategory): ItemEntity?
    fun findMinPricesByCategory(): List<MinPriceItemProjection>
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    fun deleteItemById(id: Long): Long
}

open class UserCustomRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): UserCustomRepository {
    private val itemEntity = QItemEntity.itemEntity
    override fun findItem(brand: String, category: ItemCategory): ItemEntity? {
        return jpaQueryFactory
            .selectFrom(itemEntity)
            .where(
                itemEntity.brand.eq(brand),
                itemEntity.category.eq(category),
                itemEntity.isDeleted.isFalse,
            )
            .fetchOne()
    }

    override fun findItemById(id: Long): ItemEntity? {
        return jpaQueryFactory
            .selectFrom(itemEntity)
            .where(
                itemEntity.id.eq(id),
                itemEntity.isDeleted.isFalse,
            )
            .fetchOne()
    }

    override fun findMinPriceByCategory(category: ItemCategory): ItemEntity? {
        return jpaQueryFactory
            .selectFrom(itemEntity)
            .where(
                itemEntity.category.eq(category),
                itemEntity.isDeleted.isFalse
            )
            .orderBy(itemEntity.price.asc())
            .limit(1L)
            .fetchOne()
    }

    override fun findMaxPriceByCategory(category: ItemCategory): ItemEntity? {
        return jpaQueryFactory
            .selectFrom(itemEntity)
            .where(
                itemEntity.category.eq(category),
                itemEntity.isDeleted.isFalse
            )
            .orderBy(itemEntity.price.desc())
            .limit(1L)
            .fetchOne()
    }

    override fun findMinPricesByCategory(): List<MinPriceItemProjection> {
        val subQuery = JPAExpressions
            .select(itemEntity.category, itemEntity.price.min())
            .from(itemEntity)
            .groupBy(itemEntity.category)

        return jpaQueryFactory
            .select(
                QMinPriceItemProjection(
                    itemEntity.category,
                    itemEntity.brand,
                    itemEntity.price
                )
            )
            .from(itemEntity)
            .where(
                Expressions.list(itemEntity.category, itemEntity.price).`in`(subQuery)
            )
            .fetch()
    }

    override fun deleteItemById(id: Long): Long {
        return jpaQueryFactory
            .delete(itemEntity)
            .where(
                itemEntity.id.eq(id)
            )
            .execute()
    }
}
