package kr.musinsa.api.domain.category.dto

import kr.musinsa.domain.item.model.enums.ItemCategory

data class CategoryMinMaxPriceResponse(
    val category: ItemCategory,
    val minPrice: ItemDto,
    val maxPrice: ItemDto,
)

data class ItemDto(
    val brand: String,
    val price: Int,
)
