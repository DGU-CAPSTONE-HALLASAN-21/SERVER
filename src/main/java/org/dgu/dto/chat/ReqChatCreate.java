package org.dgu.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReqChatCreate (
        @Schema(description = "생성할 대화 이름", example = "새 대화")
        String name
){

}
