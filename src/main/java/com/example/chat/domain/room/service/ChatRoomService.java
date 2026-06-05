package com.example.chat.domain.room.service;

import com.example.chat.domain.participant.entity.ChatParticipant;
import com.example.chat.domain.participant.entity.ParticipantRole;
import com.example.chat.domain.participant.repository.ChatParticipantRepository;
import com.example.chat.domain.room.dto.CreateRoomRequest;
import com.example.chat.domain.room.dto.CreateRoomResponse;
import com.example.chat.domain.room.entity.ChatRoom;
import com.example.chat.domain.room.entity.RoomType;
import com.example.chat.domain.room.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;

    public CreateRoomResponse createRoom(CreateRoomRequest request, Long userId) {
        ChatRoom room = ChatRoom.builder()
                .studyGroupId(request.studyGroupId())
                .roomType(RoomType.GROUP)
                .name(request.name())
                .isHistoryVisible(request.historyVisible())
                .build();
        chatRoomRepository.save(room);

        ChatParticipant owner = ChatParticipant.builder()
                .room(room)
                .userId(userId)
                .role(ParticipantRole.OWNER)
                .build();
        chatParticipantRepository.save(owner);

        return CreateRoomResponse.from(room);
    }
}