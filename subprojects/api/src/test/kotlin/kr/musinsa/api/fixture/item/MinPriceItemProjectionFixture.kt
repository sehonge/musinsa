package kr.musinsa.api.fixture.item

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import kr.musinsa.api.FixtureUtil
import kr.musinsa.domain.item.model.dto.ItemProjection
import kr.musinsa.domain.item.model.enums.ItemCategory

object MinPriceItemProjectionFixture {
    fun validAnyList(): List<ItemProjection> {
        return ItemCategory.values().map(::validAny)
    }

    fun validAnySameBrandList(): List<ItemProjection> {
        return ItemCategory.values().map { validAny(it, "A") }
    }

    fun validAny(category: ItemCategory): ItemProjection = FixtureUtil.monkey().giveMeBuilder<ItemProjection>()
        .setExp(ItemProjection::category, category)
        .sample()

    fun validAny(category: ItemCategory, brand: String): ItemProjection = FixtureUtil.monkey().giveMeBuilder<ItemProjection>()
        .setExp(ItemProjection::category, category)
        .setExp(ItemProjection::brand, brand)
        .sample()
}
