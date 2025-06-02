package org.dgu.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;
import org.dgu.dto.message.ResMessage;

import java.util.List;

public record ResChatDetail (
        @Schema(description = "대화 ID", example = "1")
        Long id,

        @Schema(description = "대화 이름", example = "대화1")
        String name,

        List<ResMessage> messages
) {

}
