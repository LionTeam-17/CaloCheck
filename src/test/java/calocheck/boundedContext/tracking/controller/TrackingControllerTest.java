package calocheck.boundedContext.tracking.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TrackingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("트래킹 조회")
    @WithMockUser(username = "user1") // 미리 등록된 사용자명으로 변경하세요
    public void t001() throws Exception {
        // WHEN
        var resultActions = mvc
                .perform(get("/tracking/bodyTracking"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(TrackingController.class))
                .andExpect(handler().methodName("showTracking"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("bodyTracking")));
    }

    @Test
    @DisplayName("트래킹 생성")
    @WithMockUser(username = "user1") // 미리 등록된 사용자명으로 변경하세요
    public void t002() throws Exception {
        // WHEN
        var resultActions = mvc
                .perform(post("/tracking/bodyTracking")
                        .with(csrf())
                        .param("age", "25") // 실제로 사용되는 파라미터 이름과 값으로 변경해주세요
                        .param("height", "170")
                        .param("weight", "65")
                        .param("bodyFat", "20")
                        .param("muscleMass", "50"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(TrackingController.class))
                .andExpect(handler().methodName("createTracking"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tracking/bodyTracking"));
    }
}
