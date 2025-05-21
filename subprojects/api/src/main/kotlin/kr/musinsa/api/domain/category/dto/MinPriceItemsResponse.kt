package kr.musinsa.api.domain.category.dto

import kr.musinsa.domain.item.model.dto.MinPriceItemProjection
import kr.musinsa.domain.item.model.enums.ItemCategory

data class MinPriceItemsResponse(
    val items: List<MinPriceItemDto>,
    val totalPrice: Int,
)

data class MinPriceItemDto(
    val category: ItemCategory,
    val brand: String,
    val price: Int,
) {
    companion object {
        fun from (projection: MinPriceItemProjection): MinPriceItemDto {
            return MinPriceItemDto(
                category = projection.category,
                brand = projection.brand,
                price = projection.price,
            )
        }
    }
}
