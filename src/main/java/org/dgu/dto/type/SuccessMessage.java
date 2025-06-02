package org.dgu.dto.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum SuccessMessage {
    OK(HttpStatus.OK, "OK"),
    CREATED(HttpStatus.CREATED, "CREATED");

    private final HttpStatus status;
    @Getter
    private final String message;

    SuccessMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getHttpStatusCode() {
        return status.value();
    }
}