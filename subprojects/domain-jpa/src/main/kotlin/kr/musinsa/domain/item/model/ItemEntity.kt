package kr.musinsa.domain.item.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import kr.musinsa.domain.common.BaseEntity
import kr.musinsa.domain.item.model.converter.ItemCategoryConverter
import kr.musinsa.domain.item.model.enums.ItemCategory

@Entity(name = "item")
class ItemEntity(
    @Column(
        name = "brand"
    )
    var brand: String,

    @Column(
        name = "category"
    )
    @Convert(converter = ItemCategoryConverter::class)
    var category: ItemCategory,

    @Column(
        name = "price"
    )
    var price: Int,
) : BaseEntity()
