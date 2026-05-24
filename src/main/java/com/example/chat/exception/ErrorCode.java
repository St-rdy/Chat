package com.example.chat.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 400 Bad Request
    MISSING_FIELDS(400, "MISSING_FIELDS", "필수 필드가 누락되었습니다."),
    INVALID_FIELD(400, "INVALID_FIELD", "유효하지 않은 필드값입니다."),
    CONTENT_REQUIRED(400, "CONTENT_REQUIRED", "내용을 입력해주세요."),
    MESSAGE_NOT_EDITABLE(400, "MESSAGE_NOT_EDITABLE", "파일 메시지는 수정할 수 없습니다."),
    ALREADY_JOINED(400, "ALREADY_JOINED", "이미 입장한 채팅방입니다."),
    EMPTY_FILE(400, "EMPTY_FILE", "첨부된 파일이 없습니다."),
    FILE_SIZE_EXCEEDED(400, "FILE_SIZE_EXCEEDED", "파일 크기가 100MB를 초과합니다."),
    INVALID_FILE_TYPE(400, "INVALID_FILE_TYPE", "지원하지 않는 파일 형식입니다."),

    // 401 Unauthorized
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),
    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않거나 만료된 토큰입니다."),

    // 403 Forbidden
    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),
    NOT_ROOM_OWNER(403, "NOT_ROOM_OWNER", "채팅방 방장만 수행할 수 있습니다."),
    NOT_ROOM_MEMBER(403, "NOT_ROOM_MEMBER", "채팅방 멤버가 아닙니다."),
    MESSAGE_ACCESS_DENIED(403, "MESSAGE_ACCESS_DENIED", "본인의 메시지만 수정/삭제할 수 있습니다."),
    NOT_STUDY_GROUP_MEMBER(403, "NOT_STUDY_GROUP_MEMBER", "스터디 그룹 멤버가 아닙니다."),

    // 404 Not Found
    ROOM_NOT_FOUND(404, "ROOM_NOT_FOUND", "채팅방을 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND(404, "MESSAGE_NOT_FOUND", "메시지를 찾을 수 없습니다."),
    FILE_NOT_FOUND(404, "FILE_NOT_FOUND", "파일을 찾을 수 없습니다."),
    STUDY_GROUP_NOT_FOUND(404, "STUDY_GROUP_NOT_FOUND", "스터디 그룹을 찾을 수 없습니다."),

    // 500 Internal Server Error
    DB_ERROR(500, "DB_ERROR", "데이터베이스 조회 중 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
