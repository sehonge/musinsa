package kr.musinsa.api.domain.item.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.item.dto.ItemCreateRequest
import kr.musinsa.api.domain.item.dto.ItemUpdateRequest
import kr.musinsa.api.fixture.item.ItemEntityFixture
import kr.musinsa.domain.item.model.enums.ItemCategory
import kr.musinsa.domain.item.repository.ItemRepository
import org.junit.jupiter.api.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class ItemServiceTest {
    private val itemRepository = mock<ItemRepository>()
    private val itemService = ItemService(itemRepository)

    @Nested
    @DisplayName("createItem 함수는")
    inner class CreateItemTest {
        val request = ItemCreateRequest(
            brand = "A",
            category = ItemCategory.TOP,
            price = 11200,
        )
        @Nested
        @DisplayName("상품 생성에 성공한 경우")
        inner class SuccessCase {
            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findItem(any(), any())).thenReturn(null)
            }

            @Test
            @DisplayName("true를 리턴한다")
            fun `true를 리턴한다`() {
                val result = itemService.createItem(request)

                Assertions.assertEquals(true, result)
            }
        }

        @Nested
        @DisplayName("생성하고자 하는 상품이 이미 존재하는 경우")
        inner class ItemAlreadyExistsCase {
            val itemEntity = ItemEntityFixture.validAny()

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findItem(any(), any())).thenReturn(itemEntity)
            }

            @Test
            @DisplayName("MusinsaException를 던진다")
            fun `MusinsaException를 던진다`() {
                Assertions.assertThrows(MusinsaException::class.java) {
                    itemService.createItem(request)
                }
            }
        }
    }

    @Nested
    @DisplayName("updateItem 함수는")
    inner class UpdateItemTest {
        val itemId = 200L
        val request = ItemUpdateRequest(
            price = 11200,
        )
        @Nested
        @DisplayName("상품 정보 업데이트에 성공한 경우")
        inner class SuccessCase {
            val itemEntity = ItemEntityFixture.validAny()

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findItemById(any())).thenReturn(itemEntity)
            }

            @Test
            @DisplayName("true를 리턴한다")
            fun `true를 리턴한다`() {
                val result = itemService.updateItem(itemId, request)
                Assertions.assertEquals(true, result)
            }
        }

        @Nested
        @DisplayName("업데이트 하고자 하는 상품이 존재하지 않는 경우")
        inner class ItemAlreadyExistsCase {
            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findItemById(any())).thenReturn(null)
            }

            @Test
            @DisplayName("MusinsaException를 던진다")
            fun `MusinsaException를 던진다`() {
                Assertions.assertThrows(MusinsaException::class.java) {
                    itemService.updateItem(itemId, request)
                }
            }
        }
    }

    @Nested
    @DisplayName("deleteItem 함수는")
    inner class DeleteItemTest {
        val itemId = 200L

        @Nested
        @DisplayName("상품 삭제에 성공한 경우")
        inner class SuccessCase {
            @BeforeEach
            fun setUp() {
            }

            @Test
            @DisplayName("true를 리턴한다")
            fun `true를 리턴한다`() {
                val result = itemService.deleteItem(itemId)
                Assertions.assertEquals(true, result)
            }
        }

        @Nested
        @DisplayName("업데이트 하고자 하는 상품이 존재하지 않는 경우")
        inner class ItemAlreadyExistsCase {
            @BeforeEach
            fun setUp() {
            }

            @Test
            @DisplayName("MusinsaException를 던진다")
            fun `MusinsaException를 던진다`() {
                Assertions.assertThrows(MusinsaException::class.java) {
                    itemService.deleteItem(itemId)
                }
            }
        }
    }
}
