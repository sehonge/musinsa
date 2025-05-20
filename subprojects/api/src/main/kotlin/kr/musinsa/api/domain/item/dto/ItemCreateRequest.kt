package kr.musinsa.api.domain.item.dto

import kr.musinsa.domain.item.model.enums.ItemCategory

data class ItemCreateRequest(
    val brand: String,
    val category: ItemCategory,
    val price: Int,
    val adminId: Long,
)
