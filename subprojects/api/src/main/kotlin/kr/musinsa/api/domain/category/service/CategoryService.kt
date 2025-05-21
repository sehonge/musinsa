package kr.musinsa.api.domain.category.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.domain.category.dto.Item
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
            minPrice = Item(
                brand = minPriceItem.brand,
                price = minPriceItem.price,
            ),
            maxPrice = Item(
                brand = maxPriceItem.brand,
                price = maxPriceItem.price,
            ),
        )
    }
}
