package kr.musinsa.api.domain.category.controller

import kr.musinsa.api.domain.category.dto.MinPriceBrandResponse
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.domain.category.dto.MinPriceItemsResponse
import kr.musinsa.api.domain.category.service.CategoryService
import kr.musinsa.domain.item.model.enums.ItemCategory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class CategoryController(
    private val categoryService: CategoryService,
) {
    @GetMapping("/categories/{category}/min-max")
    fun getMinMaxPriceByCategory(
        @PathVariable category: ItemCategory
    ): CategoryMinMaxPriceResponse {
        return categoryService.getMinMaxPriceByCategory(category)
    }

    @GetMapping("/categories/min-prices")
    fun getMinPricesByCategory(): MinPriceItemsResponse {
        return categoryService.getMinPricesByCategory()
    }

    @GetMapping("/categories/min-prices/brand")
    fun getMinPriceBrand(): MinPriceBrandResponse {
        return categoryService.getMinPriceBrand()
    }
}
