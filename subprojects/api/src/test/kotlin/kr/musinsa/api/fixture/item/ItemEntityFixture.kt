package kr.musinsa.api.fixture.item

import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.setExp
import kr.musinsa.api.FixtureUtil
import kr.musinsa.domain.item.model.ItemEntity

object ItemEntityFixture {
    fun validAny(): ItemEntity = FixtureUtil.monkey().giveMeBuilder<ItemEntity>()
        .setExp(ItemEntity::isDeleted, false)
        .sample()
}
