package kr.musinsa.api.domain.item.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.item.dto.ItemCreateRequest
import kr.musinsa.api.fixture.item.ItemEntityFixture
import kr.musinsa.domain.item.model.enums.ItemCategory
import kr.musinsa.domain.item.repository.ItemRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
            adminId = 200L,
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
            val exception = MusinsaException(
                clientMessage = "이미 등록한 상품입니다.",
                debugMessage = "Already Registered Item"
            )

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findItem(any(), any())).thenReturn(itemEntity)
            }

            @Test
            @DisplayName("MusinsaException를 던진다")
            fun `true를 리턴한다`() {
                Assertions.assertThrows(MusinsaException::class.java) {
                    itemService.createItem(request)
                }
            }
        }
    }
}
