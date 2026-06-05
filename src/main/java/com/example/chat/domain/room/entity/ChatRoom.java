package com.example.chat.domain.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_group_id")
    private Long studyGroupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_history_visible", nullable = false)
    private boolean isHistoryVisible;

    @Column(name = "last_message_content")
    private String lastMessageContent;

    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private ChatRoom(Long studyGroupId, RoomType roomType, String name, boolean isHistoryVisible) {
        this.studyGroupId = studyGroupId;
        this.roomType = roomType;
        this.name = name;
        this.isHistoryVisible = isHistoryVisible;
        this.createdAt = LocalDateTime.now();
    }
}
