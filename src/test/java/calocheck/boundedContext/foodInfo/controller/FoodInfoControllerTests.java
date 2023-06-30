package calocheck.boundedContext.foodInfo.controller;

import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FoodInfoControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("검색 페이지")
    void showSearchPage() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/foodInfo/search"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(FoodInfoController.class))
                .andExpect(handler().methodName("searchFoodInfo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        <input type="text" name="keyword"
                        """.stripIndent().trim())));
    }

    @Test
    @DisplayName("음식 상세 페이지")
    void showDetailsPage() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/foodInfo/details/1"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(FoodInfoController.class))
                .andExpect(handler().methodName("showDetails"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("""
                        <div class="font-bold m-3">제조사/유통사</div>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <div class="font-bold m-5">에너지(kcal)</div>
                        """.stripIndent().trim())));
    }
}
