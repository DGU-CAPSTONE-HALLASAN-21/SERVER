package org.dgu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dgu.dto.folder.ReqFolderCreate;
import org.dgu.dto.folder.ResFolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Folder", description = "폴더링 관련 API")
@RestController
@RequiredArgsConstructor
public class FolderController {

    @PostMapping("")
    @Operation(summary = "폴더 생성", description = "새로운 폴더를 생성합니다.")
    public List<ResFolder> createFolder(@RequestBody ReqFolderCreate request) {
        // request: 폴더 이름 등
        return List.of();
    }
}
