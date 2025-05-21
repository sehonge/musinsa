package kr.musinsa.api.domain.category.dto

import kr.musinsa.domain.item.model.enums.ItemCategory

data class CategoryMinMaxPriceResponse(
    val category: ItemCategory,
    val minPrice: Item,
    val maxPrice: Item,
)

data class Item(
    val brand: String,
    val price: Int,
)
