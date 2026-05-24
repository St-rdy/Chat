package com.example.chat.dto;

public record JwtUserInfo(
        Long userId,
        String nickname,
        String profileUrl
) {
}
