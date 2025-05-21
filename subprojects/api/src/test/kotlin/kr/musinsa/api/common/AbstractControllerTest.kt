package kr.musinsa.api.common

import com.fasterxml.jackson.databind.ObjectMapper
import kr.musinsa.api.domain.category.controller.CategoryController
import kr.musinsa.api.domain.category.service.CategoryService
import kr.musinsa.api.domain.item.controller.ItemController
import kr.musinsa.api.domain.item.service.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(
    ItemController::class,
    CategoryController::class,
)
@ActiveProfiles("test")
abstract class AbstractControllerTest {
    @MockBean
    lateinit var itemService: ItemService

    @MockBean
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc
}
