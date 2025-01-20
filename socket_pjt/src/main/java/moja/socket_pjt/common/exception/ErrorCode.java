package moja.socket_pjt.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements CodeInterface {
    SUCCESS(0, "SUCCESS"),
    USER_ALREADY_EXISTS(-1, "USER_ALREADY_EXISTS"),
    USER_SAVED_FAILED(-2, "USER_SAVED_FAILED"),
    USER_NOT_EXISTS(-3, "USER_NOT_EXISTS"),
    ACCESS_TOKEN_IS_NOT_EXPIRED(-200, "ACCESS_TOKEN_IS_NOT_EXPIRED"),
    TOKEN_IS_INVALID(-201, "TOKEN_IS_INVALID"),
    TOKEN_IS_EXPIRED(-202, "TOKEN_IS_EXPIRED"),
    MISMATCH_PASSWORD(-4, "MISMATCH_PASSWORD");

    private final Integer code;
    private final String message;
}
