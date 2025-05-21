package kr.musinsa.api.domain.category.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.fixture.item.ItemEntityFixture
import kr.musinsa.domain.item.model.enums.ItemCategory
import kr.musinsa.domain.item.repository.ItemRepository
import org.junit.jupiter.api.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class CategoryServiceTest {
    private val itemRepository = mock<ItemRepository>()
    private val categoryService = CategoryService(itemRepository)

    @Nested
    @DisplayName("getMinMaxPriceByCategory 함수는")
    inner class GetMinMaxPriceByCategoryTest {
        val category = ItemCategory.TOP

        @Nested
        @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회에 성공한 경우")
        inner class SuccessCase {
            val minPriceItem = ItemEntityFixture.validAny()
            val maxPriceItem = ItemEntityFixture.validAny()

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPriceByCategory(any())).thenReturn(minPriceItem)
                whenever(itemRepository.findMaxPriceByCategory(any())).thenReturn(maxPriceItem)
            }

            @Test
            @DisplayName("CategoryMinMaxPriceResponse를 리턴한다")
            fun `CategoryMinMaxPriceResponse를 리턴한다`() {
                val response = categoryService.getMinMaxPriceByCategory(category)

                Assertions.assertInstanceOf(CategoryMinMaxPriceResponse::class.java, response)
            }

            @Nested
            @DisplayName("CategoryMinMaxPriceResponse에는")
            inner class CategoryMinMaxPriceResponseTest {
                @Test
                @DisplayName("category 정보가 적재된다")
                fun `category 정보가 적재된다`() {
                    val response = categoryService.getMinMaxPriceByCategory(category)

                    Assertions.assertEquals(category, response.category)
                }

                @Test
                @DisplayName("최고 가격 브랜드의 상품 가격과 브랜드 명이 적재된다")
                fun `최고 가격 브랜드의 상품 가격과 브랜드 명이 적재된다`() {
                    val response = categoryService.getMinMaxPriceByCategory(category)

                    Assertions.assertEquals(maxPriceItem.price, response.maxPrice.price)
                    Assertions.assertEquals(maxPriceItem.brand, response.maxPrice.brand)
                }

                @Test
                @DisplayName("최저 가격 브랜드의 상품 가격과 브랜드 명이 적재된다")
                fun `최저 가격 브랜드의 상품 가격과 브랜드 명이 적재된다`() {
                    val response = categoryService.getMinMaxPriceByCategory(category)

                    Assertions.assertEquals(minPriceItem.price, response.minPrice.price)
                    Assertions.assertEquals(minPriceItem.brand, response.minPrice.brand)
                }
            }
        }

        @Nested
        @DisplayName("조회하고자 하는 카테고리에 상품이 존재하지 않는 경우")
        inner class ItemNotFoundCase {
            val exception = MusinsaException(
                clientMessage = "해당 카테고리에 상품이 존재하지 않습니다.",
                debugMessage = "Item Not Found"
            )

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPriceByCategory(any())).thenThrow(exception)
                whenever(itemRepository.findMaxPriceByCategory(any())).thenThrow(exception)
            }

            @Test
            @DisplayName("MusinsaException을 던진다")
            fun `MusinsaException을 던진다`() {
                Assertions.assertThrows(MusinsaException::class.java) {
                    categoryService.getMinMaxPriceByCategory(category)
                }
            }
        }
    }
}
