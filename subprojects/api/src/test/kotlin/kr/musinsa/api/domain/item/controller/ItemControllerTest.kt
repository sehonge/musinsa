package kr.musinsa.api.domain.item.controller

import kr.musinsa.api.common.AbstractControllerTest
import kr.musinsa.api.common.exception.MusinsaException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

internal class ItemControllerTest: AbstractControllerTest() {
    @Nested
    @DisplayName("POST 방식 통신에서")
    inner class PostMethodTest {
        @Nested
        @DisplayName("createItem 함수는")
        inner class CreateItemTest {
            val request = """
                    {
                        "brand" : "A",
                        "category" : "OUTER",
                        "price" : 5500
                    }
                """.trimIndent()

            @Nested
            @DisplayName("상품의 생성에 성공한 경우")
            inner class SuccessCase {
                @BeforeEach
                fun setUp() {
                    whenever(itemService.createItem(any())).thenReturn(true)
                }

                @Test
                @DisplayName("상태코드 200을 반환한다")
                fun `상태코드 200을 반환한다`() {
                    val resultActionsDsl = mockMvc.post("/items") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        status { isOk() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 값은 true다")
                fun `Body의 값은 true다`() {
                    val resultActionsDsl = mockMvc.post("/items") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        jsonPath("$") {
                            value(true)
                        }
                    }.andDo {
                        print()
                    }
                }
            }

            @Nested
            @DisplayName("생성하고자 하는 상품이 이미 존재하는 경우")
            inner class ItemAlreadyExistsCase {
                val exception = MusinsaException(
                    clientMessage = "이미 등록된 상품입니다.",
                    debugMessage = "Already Registered Item"
                )

                @BeforeEach
                fun setUp() {
                    whenever(itemService.createItem(any())).thenThrow(exception)
                }

                @Test
                @DisplayName("상태코드 400을 반환한다")
                fun `상태코드 400을 반환한다`() {
                    val resultActionsDsl = mockMvc.post("/items") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        status { isBadRequest() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 message에 MusinsaException의 clientMessage 정보가 들어있다")
                fun `Body의 message에 MusinsaException의 clientMessage 정보가 들어있다`() {
                    val resultActionsDsl = mockMvc.post("/items") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

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

    @Nested
    @DisplayName("PUT 방식 통신에서")
    inner class PutMethodTest {
        @Nested
        @DisplayName("updateItem 함수는")
        inner class UpdateItemTest {
            val request = """
                    {
                        "price" : 5500
                    }
                """.trimIndent()
            val itemId = 1

            @Nested
            @DisplayName("상품 정보 업데이트에 성공한 경우")
            inner class SuccessCase {
                @BeforeEach
                fun setUp() {
                    whenever(itemService.updateItem(any(), any())).thenReturn(true)
                }

                @Test
                @DisplayName("상태코드 200을 반환한다")
                fun `상태코드 200을 반환한다`() {
                    val resultActionsDsl = mockMvc.put("/items/$itemId") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        status { isOk() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 값은 true다")
                fun `Body의 값은 true다`() {
                    val resultActionsDsl = mockMvc.put("/items/$itemId") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        jsonPath("$") {
                            value(true)
                        }
                    }.andDo {
                        print()
                    }
                }
            }

            @Nested
            @DisplayName("업데이트 하고자 하는 상품이 존재하지 않는 경우")
            inner class NotExistsCase {
                val exception = MusinsaException(
                    clientMessage = "존재하지 않는 상품입니다.",
                    debugMessage = "Item NotFound"
                )

                @BeforeEach
                fun setUp() {
                    whenever(itemService.updateItem(any(), any())).thenThrow(exception)
                }

                @Test
                @DisplayName("상태코드 400을 반환한다")
                fun `상태코드 400을 반환한다`() {
                    val resultActionsDsl = mockMvc.put("/items/$itemId") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

                    resultActionsDsl.andExpect {
                        status { isBadRequest() }
                    }.andDo {
                        print()
                    }
                }

                @Test
                @DisplayName("Body의 message에 MusinsaException의 clientMessage 정보가 들어있다")
                fun `Body의 message에 MusinsaException의 clientMessage 정보가 들어있다`() {
                    val resultActionsDsl = mockMvc.put("/items/$itemId") {
                        contentType = MediaType.APPLICATION_JSON
                        content = request
                    }

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
