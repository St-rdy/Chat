package com.example.chat.common;

import com.example.chat.exception.BusinessException;
import com.example.chat.exception.ErrorCode;
import com.example.chat.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @RestController
    static class TestController {

        @GetMapping("/test/unexpected")
        void throwUnexpectedException() {
            throw new RuntimeException("예상치 못한 에러");
        }

        @GetMapping("/test/{errorCode}")
        void throwBusinessException(@PathVariable String errorCode) {
            throw new BusinessException(ErrorCode.valueOf(errorCode));
        }
    }

    @Nested
    @DisplayName("BusinessException 처리")
    class BusinessExceptionHandling {

        @Test
        @DisplayName("실패: 400 에러코드는 400 상태로 응답한다")
        void bad_request_mapping() throws Exception {
            // given
            String errorCode = "MISSING_FIELDS";

            // when & then
            mockMvc.perform(get("/test/{errorCode}", errorCode))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value(errorCode));
        }

        @Test
        @DisplayName("실패: 401 에러코드는 401 상태로 응답한다")
        void unauthorized_mapping() throws Exception {
            // given
            String errorCode = "UNAUTHORIZED";

            // when & then
            mockMvc.perform(get("/test/{errorCode}", errorCode))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(401))
                    .andExpect(jsonPath("$.status").value(errorCode));
        }

        @Test
        @DisplayName("실패: INVALID_TOKEN은 401 상태로 응답한다")
        void invalid_token_mapping() throws Exception {
            // given
            String errorCode = "INVALID_TOKEN";

            // when & then
            mockMvc.perform(get("/test/{errorCode}", errorCode))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(401))
                    .andExpect(jsonPath("$.status").value(errorCode));
        }

        @Test
        @DisplayName("실패: 403 에러코드는 403 상태와 메시지를 응답한다")
        void forbidden_mapping() throws Exception {
            // given
            String errorCode = "NOT_ROOM_MEMBER";

            // when & then
            mockMvc.perform(get("/test/{errorCode}", errorCode))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value(403))
                    .andExpect(jsonPath("$.status").value(errorCode))
                    .andExpect(jsonPath("$.message").value("채팅방 멤버가 아닙니다."));
        }

        @Test
        @DisplayName("실패: 404 에러코드는 404 상태로 응답한다")
        void not_found_mapping() throws Exception {
            // given
            String errorCode = "ROOM_NOT_FOUND";

            // when & then
            mockMvc.perform(get("/test/{errorCode}", errorCode))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(404))
                    .andExpect(jsonPath("$.status").value(errorCode));
        }
    }

    @Nested
    @DisplayName("예상치 못한 예외 처리")
    class UnexpectedExceptionHandling {

        @Test
        @DisplayName("실패: 처리되지 않은 예외는 500 상태로 응답한다")
        void unhandled_exception_returns_500() throws Exception {
            // when & then
            mockMvc.perform(get("/test/unexpected"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.code").value(500))
                    .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
        }
    }
}
