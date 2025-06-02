package org.dgu.dto.message;


import java.time.LocalDateTime;

public record ResMessage(
        Long messageId,
        String sender,
        String content,
        LocalDateTime timestamp
) {

}
