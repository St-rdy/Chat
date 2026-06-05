package com.example.chat.domain.room.controller;

import com.example.chat.domain.room.dto.CreateRoomRequest;
import com.example.chat.domain.room.dto.CreateRoomResponse;
import com.example.chat.domain.room.service.ChatRoomService;
import com.example.chat.dto.JwtUserInfo;
import com.example.chat.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ChatRoomControllerTest {

    @Mock private ChatRoomService chatRoomService;
    @InjectMocks private ChatRoomController chatRoomController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final JwtUserInfo userInfo = new JwtUserInfo(1L, "테스터", "https://profile.jpg");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(chatRoomController)
                .setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userInfo, null, List.of())
        );
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    @DisplayName("POST /api/v1/chat/rooms")
    class CreateRoom {

        @Test
        @DisplayName("성공: 채팅방 생성 성공 시 200과 생성된 채팅방 정보를 반환한다")
        void createRoom_success() throws Exception {
            // given
            CreateRoomRequest request = new CreateRoomRequest(1L, "알고리즘 스터디", true);
            CreateRoomResponse response = new CreateRoomResponse(1L, 1L, "알고리즘 스터디", true, LocalDateTime.now());
            given(chatRoomService.createRoom(any(), eq(1L))).willReturn(response);

            // when & then
            mockMvc.perform(post("/api/v1/chat/rooms")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.studyGroupId").value(1))
                    .andExpect(jsonPath("$.data.name").value("알고리즘 스터디"))
                    .andExpect(jsonPath("$.data.historyVisible").value(true));
        }

        @Test
        @DisplayName("실패: studyGroupId가 없으면 400을 반환한다")
        void createRoom_missing_studyGroupId() throws Exception {
            // given
            String body = "{\"name\":\"알고리즘 스터디\",\"historyVisible\":true}";

            // when & then
            mockMvc.perform(post("/api/v1/chat/rooms")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("MISSING_FIELDS"));
        }

        @Test
        @DisplayName("실패: name이 없으면 400을 반환한다")
        void createRoom_missing_name() throws Exception {
            // given
            String body = "{\"studyGroupId\":1,\"historyVisible\":true}";

            // when & then
            mockMvc.perform(post("/api/v1/chat/rooms")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("MISSING_FIELDS"));
        }

        @Test
        @DisplayName("실패: historyVisible이 없으면 400을 반환한다")
        void createRoom_missing_historyVisible() throws Exception {
            // given
            String body = "{\"studyGroupId\":1,\"name\":\"알고리즘 스터디\"}";

            // when & then
            mockMvc.perform(post("/api/v1/chat/rooms")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.status").value("MISSING_FIELDS"));
        }
    }
}
