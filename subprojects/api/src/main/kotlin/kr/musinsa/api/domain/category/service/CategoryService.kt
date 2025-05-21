package kr.musinsa.api.domain.category.service

import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
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
        return TODO()
    }
}
