//package calocheck.boundedContext.cartFoodInfo.controller;
//
//import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
//import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class CartFoodInfoControllerTests {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    private CartFoodInfoService cartFoodInfoService;
//
//    @Test
//    @DisplayName("장바구니 리스트 페이지 요청")
//    @WithMockUser("user1")
//    void showCartListPage() throws Exception {
//        // WHEN
//        ResultActions resultActions = mvc
//                .perform(get("/cartFoodInfo/list"))
//                .andDo(print());
//
//        // THEN
//        resultActions
//                .andExpect(handler().handlerType(CartFoodInfoController.class))
//                .andExpect(handler().methodName("showCartList"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("""
//                        장바구니 음식 목록
//                        """.stripIndent().trim())));
//    }
//
//    @Test
//    @DisplayName("장바구니 추가 요청")
//    @WithMockUser("user2")
//    void addCartFoodInfoRequest() throws Exception {
//        // WHEN
//        ResultActions resultActions = mvc
//                .perform(post("/cartFoodInfo/add")
//                        .with(csrf())
//                        .param("foodId", "5")
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("success"));
//
//        List<CartFoodInfo> cartFoodInfos = cartFoodInfoService.findAllByMember(2L);
//
//        assertThat(cartFoodInfos.size()).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("장바구니 삭제 요청")
//    @WithMockUser("user2")
//    void removeCartFoodInfoRequest() throws Exception {
//        // WHEN
//        ResultActions resultActions = mvc
//                .perform(post("/cartFoodInfo/remove")
//                        .with(csrf())
//                        .param("foodId", "5")
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("success"));
//
//        List<CartFoodInfo> cartFoodInfos = cartFoodInfoService.findAllByMember(2L);
//
//        assertThat(cartFoodInfos.size()).isEqualTo(0);
//    }
//
//    @Test
//    @DisplayName("장바구니 수량 수정 요청")
//    @WithMockUser("user2")
//    void updateCartFoodInfoRequest() throws Exception {
//        // WHEN
//        ResultActions resultActions1 = mvc
//                .perform(post("/cartFoodInfo/add")
//                        .with(csrf())
//                        .param("foodId", "1")
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("success"));
//
//        ResultActions resultActions2 = mvc
//                .perform(post("/cartFoodInfo/update")
//                        .with(csrf())
//                        .param("foodId", "1")
//                        .param("quantity", "3")
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("success"));
//
//        List<CartFoodInfo> cartFoodInfos = cartFoodInfoService.findAllByMember(2L);
//
//        assertThat(cartFoodInfos.size()).isEqualTo(1);
//        assertThat(cartFoodInfos.get(0).getQuantity()).isEqualTo(3);
//    }
//
//    @Test
//    @DisplayName("장바구니 항목 총 합계 페이지")
//    @WithMockUser("user2")
//    void showCartTotalPage() throws Exception {
//        // WHEN
//        ResultActions resultActions = mvc
//                .perform(get("/cartFoodInfo/total"))
//                .andDo(print());
//
//        // THEN
//        resultActions
//                .andExpect(handler().handlerType(CartFoodInfoController.class))
//                .andExpect(handler().methodName("showCartTotal"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(content().string(containsString("""
//                        <canvas id="nutrientChart"></canvas>
//                        """.stripIndent().trim())));
//    }
//}
