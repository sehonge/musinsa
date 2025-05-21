package kr.musinsa.api.domain.category.service

import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.domain.category.dto.MinPriceBrandResponse
import kr.musinsa.api.domain.category.dto.MinPriceItemsResponse
import kr.musinsa.api.fixture.item.ItemEntityFixture
import kr.musinsa.api.fixture.item.MinPriceItemProjectionFixture
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

    @Nested
    @DisplayName("getMinPricesByCategory 함수는")
    inner class GetMinPricesByCategoryTest {
        @Nested
        @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회에 성공한 경우")
        inner class SuccessCase {
            val minPriceItemProjectionList = MinPriceItemProjectionFixture.validAnyList()

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPricesByCategory()).thenReturn(minPriceItemProjectionList)
            }

            @Test
            @DisplayName("MinPriceItemsResponse를 리턴한다")
            fun `MinPriceItemsResponse를 리턴한다`() {
                val response = categoryService.getMinPricesByCategory()

                Assertions.assertInstanceOf(MinPriceItemsResponse::class.java, response)
            }

            @Nested
            @DisplayName("MinPriceItemsResponse에는")
            inner class MinPriceItemsResponseTest {
                @Test
                @DisplayName("totalPrice 정보가 적재된다")
                fun `totalPrice 정보가 적재된다`() {
                    val response = categoryService.getMinPricesByCategory()
                    val expectedPrice = minPriceItemProjectionList.sumOf { it.price }

                    Assertions.assertEquals(expectedPrice, response.totalPrice)
                }

                @Test
                @DisplayName("총 카테고리 개수만큼 MinPriceItemDto 정보가 적재된다")
                fun `총 카테고리 개수만큼 MinPriceItemDto 정보가 적재된다`() {
                    val response = categoryService.getMinPricesByCategory()

                    Assertions.assertEquals(ItemCategory.values().size, response.items.size)
                }
            }
        }

        @Nested
        @DisplayName("일부 카테고리의 최저가 상품이 누락된 경우")
        inner class MissingCategoryCase {
            val partialItems = listOf(
                MinPriceItemProjectionFixture.validAny(ItemCategory.TOP),
                MinPriceItemProjectionFixture.validAny(ItemCategory.OUTER),
            )

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPricesByCategory()).thenReturn(partialItems)
            }

            @Test
            @DisplayName("MusinsaException을 던진다")
            fun `MusinsaException을 던진다`() {
                assertThrows<MusinsaException> {
                    categoryService.getMinPricesByCategory()
                }
            }
        }
    }

    @Nested
    @DisplayName("getMinPriceBrand 함수는")
    inner class GetMinPriceBrandTest {
        @Nested
        @DisplayName("최저가의 단일 브랜드 조회에 성공한 경우")
        inner class SuccessCase {
            val minPriceItemProjectionList = MinPriceItemProjectionFixture.validAnySameBrandList()

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPriceItemsGroupByBrandAndCategory()).thenReturn(minPriceItemProjectionList)
            }

            @Test
            @DisplayName("MinPriceBrandResponse를 리턴한다")
            fun `MinPriceBrandResponse를 리턴한다`() {
                val response = categoryService.getMinPriceBrand()

                Assertions.assertInstanceOf(MinPriceBrandResponse::class.java, response)
            }

            @Nested
            @DisplayName("MinPriceBrandResponse에는")
            inner class MinPriceItemsResponseTest {
                @Test
                @DisplayName("totalPrice 정보가 적재된다")
                fun `totalPrice 정보가 적재된다`() {
                    val response = categoryService.getMinPriceBrand()
                    val expectedPrice = minPriceItemProjectionList.sumOf { it.price }

                    Assertions.assertEquals(expectedPrice, response.totalPrice)
                }

                @Test
                @DisplayName("총 카테고리 개수만큼 CategoryPriceDto 정보가 적재된다")
                fun `총 카테고리 개수만큼 CategoryPriceDto 정보가 적재된다`() {
                    val response = categoryService.getMinPriceBrand()

                    Assertions.assertEquals(ItemCategory.values().size, response.categories.size)
                }
            }
        }

        @Nested
        @DisplayName("일부 카테고리의 최저가 상품이 누락된 경우")
        inner class MissingCategoryCase {
            val partialItems = listOf(
                MinPriceItemProjectionFixture.validAny(ItemCategory.TOP),
                MinPriceItemProjectionFixture.validAny(ItemCategory.OUTER),
            )

            @BeforeEach
            fun setUp() {
                whenever(itemRepository.findMinPricesByCategory()).thenReturn(partialItems)
            }

            @Test
            @DisplayName("MusinsaException을 던진다")
            fun `MusinsaException을 던진다`() {
                assertThrows<MusinsaException> {
                    categoryService.getMinPriceBrand()
                }
            }
        }
    }
}
