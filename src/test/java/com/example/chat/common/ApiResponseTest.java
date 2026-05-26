package com.example.chat.common;

import com.example.chat.dto.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Nested
    @DisplayName("success()")
    class Success {

        @Test
        @DisplayName("성공: code=200, status=SUCCESS로 응답을 반환한다")
        void success_format() {
            // given
            String message = "조회 성공";
            String data = "data";

            // when
            ApiResponse<String> response = ApiResponse.success(message, data);

            // then
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.status()).isEqualTo("SUCCESS");
            assertThat(response.message()).isEqualTo(message);
            assertThat(response.data()).isEqualTo(data);
        }

        @Test
        @DisplayName("성공: data가 null이어도 정상 반환한다")
        void success_null_data() {
            // when
            ApiResponse<Void> response = ApiResponse.success("처리 완료", null);

            // then
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.status()).isEqualTo("SUCCESS");
            assertThat(response.data()).isNull();
        }
    }

    @Nested
    @DisplayName("created()")
    class Created {

        @Test
        @DisplayName("성공: code=201, status=SUCCESS로 응답을 반환한다")
        void created_format() {
            // given
            String message = "생성 성공";
            String data = "data";

            // when
            ApiResponse<String> response = ApiResponse.created(message, data);

            // then
            assertThat(response.code()).isEqualTo(201);
            assertThat(response.status()).isEqualTo("SUCCESS");
            assertThat(response.message()).isEqualTo(message);
            assertThat(response.data()).isEqualTo(data);
        }
    }

    @Nested
    @DisplayName("error()")
    class Error {

        @Test
        @DisplayName("실패: code/status/message를 포함하고 data는 null로 반환한다")
        void error_format() {
            // given
            int code = 404;
            String status = "ROOM_NOT_FOUND";
            String message = "채팅방을 찾을 수 없습니다.";

            // when
            ApiResponse<Void> response = ApiResponse.error(code, status, message);

            // then
            assertThat(response.code()).isEqualTo(code);
            assertThat(response.status()).isEqualTo(status);
            assertThat(response.message()).isEqualTo(message);
            assertThat(response.data()).isNull();
        }
    }
}
