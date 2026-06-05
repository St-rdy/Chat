package com.example.chat.domain.participant.repository;

import com.example.chat.domain.participant.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
}
