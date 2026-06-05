package com.example.chat.domain.room.dto;

import com.example.chat.domain.room.entity.ChatRoom;

import java.time.LocalDateTime;

public record CreateRoomResponse(
        Long id,
        Long studyGroupId,
        String name,
        boolean historyVisible,
        LocalDateTime createdAt
) {
    public static CreateRoomResponse from(ChatRoom room) {
        return new CreateRoomResponse(
                room.getId(),
                room.getStudyGroupId(),
                room.getName(),
                room.isHistoryVisible(),
                room.getCreatedAt()
        );
    }
}
