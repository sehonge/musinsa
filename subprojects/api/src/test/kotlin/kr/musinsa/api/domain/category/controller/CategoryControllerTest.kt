package kr.musinsa.api.domain.category.controller

import kr.musinsa.api.common.AbstractControllerTest
import kr.musinsa.api.common.exception.MusinsaException
import kr.musinsa.api.domain.category.dto.CategoryMinMaxPriceResponse
import kr.musinsa.api.domain.category.dto.CategoryPriceDto
import kr.musinsa.api.domain.category.dto.ItemDto
import kr.musinsa.api.domain.category.dto.MinPriceBrandResponse
import kr.musinsa.domain.item.model.enums.ItemCategory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.get

internal class CategoryControllerTest: AbstractControllerTest() {
    @Nested
    @DisplayName("GET 방식 통신에서")
    inner class GetMethodTest {
        @Nested
        @DisplayName("getMinMaxPriceByCategory 함수는")
        inner class GetMinMaxPriceByCategoryTest {
            val category = "TOP"

            @Nested
            @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 삼품 가격 조회에 성공한 경우")
            inner class SuccessCase {
                val response = CategoryMinMaxPriceResponse(
                    category = ItemCategory.TOP,
                    minPrice = ItemDto(
                        brand = "C",
                        price = 10_000,
                    ),
                    maxPrice = ItemDto(
                        brand = "I",
                        price = 11_400,
                    )
                )

                @BeforeEach
                fun setUp() {
                    whenever(categoryService.getMinMaxPriceByCategory(any())).thenReturn(response)
                }

                @Test
                @DisplayName("상태코드 200을 반환한다")
                fun `상태코드 200을 반환한다`() {
                    val resultActionsDsl = mockMvc.get("/categories/$category/min-max")

                    resultActionsDsl.andExpect {
                        status { isOk() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 minPrice에 카테고리의 최저 가격 브랜드와 상품 가격 정보가 있다")
                fun `Body의 minPrice에 카테고리의 최저 가격 브랜드와 상품 가격 정보가 있다`() {
                    val resultActionsDsl = mockMvc.get("/categories/$category/min-max")

                    resultActionsDsl.andExpect {
                        jsonPath("$.minPrice.brand") {
                            value(response.minPrice.brand)
                        }
                        jsonPath("$.minPrice.price") {
                            value(response.minPrice.price)
                        }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 maxPrice에 카테고리의 최고 가격 브랜드와 상품 가격 정보가 있다")
                fun `Body의 maxPrice에 카테고리의 최고 가격 브랜드와 상품 가격 정보가 있다`() {
                    val resultActionsDsl = mockMvc.get("/categories/$category/min-max")

                    resultActionsDsl.andExpect {
                        jsonPath("$.maxPrice.brand") {
                            value(response.maxPrice.brand)
                        }
                        jsonPath("$.maxPrice.price") {
                            value(response.maxPrice.price)
                        }
                    }.andDo {
                        print()
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
                    whenever(categoryService.getMinMaxPriceByCategory(any())).thenThrow(exception)
                }

                @Test
                @DisplayName("상태코드 400을 반환한다")
                fun `상태코드 400을 반환한다`() {
                    val resultActionsDsl = mockMvc.get("/categories/$category/min-max")

                    resultActionsDsl.andExpect {
                        status { isBadRequest() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 message에 MusinsaException의 clientMessage 정보가 들어있다")
                fun `Body의 message에 MusinsaException의 clientMessage 정보가 들어있다`() {
                    val resultActionsDsl = mockMvc.get("/categories/$category/min-max")

                    resultActionsDsl.andExpect {
                        jsonPath("$.message") {
                            value(exception.clientMessage)
                        }
                    }.andDo {
                        print()
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
                val response = MinPriceBrandResponse(
                    brand = "A",
                    categories = listOf(
                        CategoryPriceDto(
                            category = ItemCategory.TOP,
                            price = 10_100,
                        ),
                        CategoryPriceDto(
                            category = ItemCategory.OUTER,
                            price = 5_100,
                        ),
                        CategoryPriceDto(
                            category = ItemCategory.BOTTOM,
                            price = 3_000,
                        ),
                    ),
                    totalPrice = 36_100,
                )

                @BeforeEach
                fun setUp() {
                    whenever(categoryService.getMinPriceBrand()).thenReturn(response)
                }

                @Test
                @DisplayName("상태코드 200을 반환한다")
                fun `상태코드 200을 반환한다`() {
                    val resultActionsDsl = mockMvc.get("/categories/min-prices/brand")

                    resultActionsDsl.andExpect {
                        status { isOk() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 minPrice에 카테고리의 최저 가격 브랜드와 상품 총 가격 정보가 있다")
                fun `Body의 minPrice에 카테고리의 최저 가격 브랜드와 상품 총 가격 정보가 있다`() {
                    val resultActionsDsl = mockMvc.get("/categories/min-prices/brand")

                    resultActionsDsl.andExpect {
                        jsonPath("$.brand") {
                            value(response.brand)
                        }
                        jsonPath("$.totalPrice") {
                            value(response.totalPrice)
                        }
                    }.andDo {
                        print()
                    }
                }
            }

            @Nested
            @DisplayName("일부 카테고리의 최저가 상품이 누락된 경우")
            inner class MissingCategoryCase {
                val exception = MusinsaException(clientMessage = "모든 카테고리를 포함하는 브랜드가 없습니다.")

                @BeforeEach
                fun setUp() {
                    whenever(categoryService.getMinPriceBrand()).thenThrow(exception)
                }

                @Test
                @DisplayName("상태코드 400을 반환한다")
                fun `상태코드 400을 반환한다`() {
                    val resultActionsDsl = mockMvc.get("/categories/min-prices/brand")

                    resultActionsDsl.andExpect {
                        status { isBadRequest() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 message에 MusinsaException의 clientMessage 정보가 들어있다")
                fun `Body의 message에 MusinsaException의 clientMessage 정보가 들어있다`() {
                    val resultActionsDsl = mockMvc.get("/categories/min-prices/brand")

                    resultActionsDsl.andExpect {
                        jsonPath("$.message") {
                            value(exception.clientMessage)
                        }
                    }.andDo {
                        print()
                    }
                }
            }
        }
    }
}
