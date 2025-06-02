package org.dgu.dto.chat;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResChat (
        @Schema(description = "대화 ID", example = "1")
        Long id,

        @Schema(description = "대화 이름", example = "대화1")
        String name
) {

}
