package org.dgu.dto.folder;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ReqFolderCreate (
        @Schema(description = "생성할 폴더 이름", example = "새 폴더")
        String name,

        UUID uuid
){

}
