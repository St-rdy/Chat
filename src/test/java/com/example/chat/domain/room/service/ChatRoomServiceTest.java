package com.example.chat.domain.room.service;

import com.example.chat.domain.participant.entity.ChatParticipant;
import com.example.chat.domain.participant.entity.ParticipantRole;
import com.example.chat.domain.participant.repository.ChatParticipantRepository;
import com.example.chat.domain.room.dto.CreateRoomRequest;
import com.example.chat.domain.room.dto.CreateRoomResponse;
import com.example.chat.domain.room.entity.ChatRoom;
import com.example.chat.domain.room.repository.ChatRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock private ChatRoomRepository chatRoomRepository;
    @Mock private ChatParticipantRepository chatParticipantRepository;
    @InjectMocks private ChatRoomService chatRoomService;

    @Nested
    @DisplayName("createRoom()")
    class CreateRoom {

        @Test
        @DisplayName("성공: 채팅방과 OWNER 참여자가 저장된다")
        void createRoom_saves_room_and_owner() {
            // given
            CreateRoomRequest request = new CreateRoomRequest(1L, "알고리즘 스터디", true);
            Long userId = 10L;

            // when
            chatRoomService.createRoom(request, userId);

            // then
            verify(chatRoomRepository).save(any(ChatRoom.class));

            ArgumentCaptor<ChatParticipant> captor = ArgumentCaptor.forClass(ChatParticipant.class);
            verify(chatParticipantRepository).save(captor.capture());
            assertThat(captor.getValue().getRole()).isEqualTo(ParticipantRole.OWNER);
            assertThat(captor.getValue().getUserId()).isEqualTo(userId);
        }

        @Test
        @DisplayName("성공: 응답에 요청 필드가 반영된다")
        void createRoom_returns_correct_response() {
            // given
            CreateRoomRequest request = new CreateRoomRequest(1L, "알고리즘 스터디", true);

            // when
            CreateRoomResponse response = chatRoomService.createRoom(request, 10L);

            // then
            assertThat(response.studyGroupId()).isEqualTo(1L);
            assertThat(response.name()).isEqualTo("알고리즘 스터디");
            assertThat(response.historyVisible()).isTrue();
        }
    }
}
