package org.dgu.dto.folder;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ReqFolderRename (
        @Schema(description = "수정할 폴더명", example = "새 폴더2")
        String name,

        UUID uuid
){

}
