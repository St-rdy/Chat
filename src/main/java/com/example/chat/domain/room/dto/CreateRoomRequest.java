package com.example.chat.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
        @NotNull Long studyGroupId,
        @NotBlank String name,
        @NotNull Boolean historyVisible
) {
}
