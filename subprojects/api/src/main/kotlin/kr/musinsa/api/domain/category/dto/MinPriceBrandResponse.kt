package kr.musinsa.api.domain.category.dto

import kr.musinsa.domain.item.model.dto.ItemProjection
import kr.musinsa.domain.item.model.enums.ItemCategory

data class MinPriceBrandResponse(
    val brand: String,
    val categories: List<CategoryPriceDto>,
    val totalPrice: Int,
)

data class CategoryPriceDto(
    val category: ItemCategory,
    val price: Int,
) {
    companion object {
        fun from(itemProjection: ItemProjection): CategoryPriceDto {
            return CategoryPriceDto(
                category = itemProjection.category,
                price = itemProjection.price,
            )
        }
    }
}
