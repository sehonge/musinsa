package kr.musinsa.api.domain.item.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.item.dto.ItemCreateRequest
import kr.musinsa.api.domain.item.dto.ItemUpdateRequest
import kr.musinsa.domain.item.model.ItemEntity
import kr.musinsa.domain.item.model.enums.ItemCategory
import kr.musinsa.domain.item.repository.ItemRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemRepository: ItemRepository,
) {
    @Transactional
    fun createItem(request: ItemCreateRequest): Boolean {
        checkAlreadyRegistered(request.brand, request.category)
        itemRepository.save(createItemEntity(request))

        return true
    }

    @Transactional
    fun updateItem(itemId: Long, request: ItemUpdateRequest): Boolean {
        val item = itemRepository.findItemById(itemId) ?: throw MusinsaException(
            clientMessage = "존재하지 않는 상품입니다.",
            debugMessage = "Item NotFound. $itemId"
        )
        item.price = request.price

        return true
    }

    @Transactional
    fun deleteItem(itemId: Long): Boolean {
        itemRepository.findItemById(itemId) ?: throw MusinsaException(
            clientMessage = "존재하지 않는 상품입니다.",
            debugMessage = "Item NotFound. $itemId"
        )
        itemRepository.deleteItemById(itemId)

        return true
    }

    private fun checkAlreadyRegistered(brand: String, category: ItemCategory) {
        val item = itemRepository.findItem(brand, category)
        if (item != null) {
            throw MusinsaException(
                clientMessage = "이미 등록된 상품입니다.",
                debugMessage = "Already Registered Item. brand: $brand, category: $category"
            )
        }
    }

    private fun createItemEntity(request: ItemCreateRequest): ItemEntity {
        return ItemEntity(
            brand = request.brand,
            category = request.category,
            price = request.price,
        )
    }
}
