package org.dgu.dto.folder;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReqFolderRename (
        @Schema(description = "수정할 폴더 ID", example = "1")
        Long Id,

        @Schema(description = "수정할 폴더명", example = "새 폴더2")
        String name
){

}
