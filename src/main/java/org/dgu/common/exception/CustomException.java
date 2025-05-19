package org.dgu.common.exception;

import lombok.Getter;
import org.dgu.dto.type.ErrorMessage;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public int getHttpStatusCode() {
        return errorMessage.getHttpStatusCode();
    }
}