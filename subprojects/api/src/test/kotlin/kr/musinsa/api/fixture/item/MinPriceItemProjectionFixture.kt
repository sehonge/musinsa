package kr.musinsa.api.fixture.item

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import kr.musinsa.api.FixtureUtil
import kr.musinsa.domain.item.model.dto.MinPriceItemProjection
import kr.musinsa.domain.item.model.enums.ItemCategory

object MinPriceItemProjectionFixture {
    fun validAnyList(): List<MinPriceItemProjection> {
        return ItemCategory.values().map(::validAny)
    }

    fun validAny(category: ItemCategory): MinPriceItemProjection = FixtureUtil.monkey().giveMeBuilder<MinPriceItemProjection>()
        .setExp(MinPriceItemProjection::category, category)
        .sample()
}
