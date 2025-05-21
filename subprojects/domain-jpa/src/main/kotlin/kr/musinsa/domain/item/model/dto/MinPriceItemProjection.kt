package kr.musinsa.domain.item.model.dto

import com.querydsl.core.annotations.QueryProjection
import kr.musinsa.domain.item.model.enums.ItemCategory

data class MinPriceItemProjection @QueryProjection constructor(
    val category: ItemCategory,
    val brand: String,
    val price: Int,
)
