package org.dgu.dto.folder;

import io.swagger.v3.oas.annotations.media.Schema;
import org.dgu.dto.chat.ResChat;

import java.util.List;

public record ResFolderDetail (
        @Schema(description = "폴더 ID", example = "1")
        Long id,

        @Schema(description = "폴더 이름", example = "폴더1")
        String name,

        List<ResChat> chats
) {

}
