package com.example.chat.domain.room.controller;

import com.example.chat.domain.room.dto.CreateRoomRequest;
import com.example.chat.domain.room.dto.CreateRoomResponse;
import com.example.chat.domain.room.service.ChatRoomService;
import com.example.chat.dto.ApiResponse;
import com.example.chat.dto.JwtUserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateRoomResponse>> createRoom(
            @RequestBody @Valid CreateRoomRequest request,
            @AuthenticationPrincipal JwtUserInfo userInfo) {
        CreateRoomResponse response = chatRoomService.createRoom(request, userInfo.userId());
        return ResponseEntity.ok(ApiResponse.success("채팅방 생성 성공", response));
    }
}
