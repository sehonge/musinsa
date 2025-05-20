package kr.musinsa.api.domain.item.service

import kr.musinsa.api.domain.item.dto.ItemRequest
import kr.musinsa.domain.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemRepository: ItemRepository,
) {
    @Transactional
    fun createItem(request: ItemRequest): Boolean {
        return TODO()
    }
}
