package moja.socket_pjt.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final CodeInterface codeInterface;

    public CustomException(CodeInterface v) {
        super(v.getMessage());
        this.codeInterface = v;
    }

    public CustomException(CodeInterface v, String message) {
        super(v.getMessage() + message);
        this.codeInterface = v;
    }
}

