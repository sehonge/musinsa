package kr.musinsa.api.domain.category.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.domain.category.dto.ItemDto
import kr.musinsa.api.domain.category.dto.MinPriceItemDto
import kr.musinsa.api.domain.category.dto.MinPriceItemsResponse
import kr.musinsa.domain.item.model.enums.ItemCategory
import kr.musinsa.domain.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val itemRepository: ItemRepository,
) {
    @Transactional(readOnly = true)
    fun getMinMaxPriceByCategory(category: ItemCategory): CategoryMinMaxPriceResponse {
        val minPriceItem = itemRepository.findMinPriceByCategory(category) ?: throw MusinsaException(
            clientMessage = "해당 카테코리에 상품이 존재하지 않습니다."
        )
        val maxPriceItem = itemRepository.findMaxPriceByCategory(category) ?: throw MusinsaException(
            clientMessage = "해당 카테코리에 상품이 존재하지 않습니다."
        )

        return CategoryMinMaxPriceResponse(
            category = category,
            minPrice = ItemDto(
                brand = minPriceItem.brand,
                price = minPriceItem.price,
            ),
            maxPrice = ItemDto(
                brand = maxPriceItem.brand,
                price = maxPriceItem.price,
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getMinPricesByCategory(): MinPriceItemsResponse {
        val minPriceItemDtoList = getMinPriceItemDtoList()
        val totalPrice = minPriceItemDtoList.sumOf { it.price }

        return MinPriceItemsResponse(
            items = minPriceItemDtoList,
            totalPrice = totalPrice
        )
    }

    private fun getMinPriceItemDtoList(): List<MinPriceItemDto> {
        val minPriceItemDtoList = itemRepository
            .findMinPricesByCategory()
            .map(MinPriceItemDto.Companion::from)

        val categoryList = minPriceItemDtoList.map { it.category }.toSet()
        val allItemCategoryList = ItemCategory.values().toSet()
        if (allItemCategoryList != categoryList) {
            throw MusinsaException(
                clientMessage = "일부 카테고리의 최저가 상품이 누락되어 있습니다.",
                debugMessage = "Expected: $allItemCategoryList, Found: $categoryList"
            )
        }

        return minPriceItemDtoList
    }
}
