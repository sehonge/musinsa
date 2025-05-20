package kr.musinsa.api.domain.item.controller

import kr.musinsa.api.domain.item.dto.ItemRequest
import kr.musinsa.api.domain.item.service.ItemService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController(
    private val itemService: ItemService,
) {
    @PostMapping
    fun createItem(
        @RequestBody request: ItemRequest,
    ): Boolean {
        return itemService.createItem(request)
    }
}
