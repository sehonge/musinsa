package kr.musinsa.api.domain.item.controller

import kr.musinsa.api.domain.item.dto.ItemCreateRequest
import kr.musinsa.api.domain.item.dto.ItemUpdateRequest
import kr.musinsa.api.domain.item.service.ItemService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ItemController(
    private val itemService: ItemService,
) {
    @PostMapping("/items")
    fun createItem(
        @RequestBody request: ItemCreateRequest,
    ): Boolean {
        return itemService.createItem(request)
    }

    @PutMapping("/items/{itemId}")
    fun updateItem(
        @PathVariable itemId: Long,
        @RequestBody request: ItemUpdateRequest,
    ): Boolean {
        return itemService.updateItem(itemId, request)
    }
}
