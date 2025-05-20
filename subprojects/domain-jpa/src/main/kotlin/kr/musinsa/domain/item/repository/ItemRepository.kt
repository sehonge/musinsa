package kr.musinsa.domain.item.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.musinsa.domain.item.model.ItemEntity
import kr.musinsa.domain.item.model.QItemEntity
import kr.musinsa.domain.item.model.enums.ItemCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<ItemEntity, Long>, UserCustomRepository

interface UserCustomRepository {
    fun findItem(brand: String, category: ItemCategory): ItemEntity?
    fun findItemById(id: Long): ItemEntity?
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
}
