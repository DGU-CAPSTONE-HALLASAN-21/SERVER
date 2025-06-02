package org.dgu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dgu.dto.chat.ResChat;
import org.dgu.dto.folder.*;
import org.dgu.service.FolderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Folder", description = "폴더 API")
@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("")
    @Operation(summary = "폴더 생성", description = "새로운 폴더를 생성합니다.")
    public List<ResFolder> createFolder(@RequestBody ReqFolderCreate request) {
        // request: 폴더 이름 등
        return folderService.createFolder(request);
    }

    @PatchMapping("/{folderId}")
    @Operation(summary = "폴더 이름 수정", description = "폴더명을 수정합니다.")
    public List<ResFolder> updateFolderName(
            @PathVariable Long folderId,
            @RequestBody ReqFolderRename request) {

        return folderService.updateFolderName(folderId, request);
    }

    @DeleteMapping("/{folderId}")
    @Operation(summary = "폴더 삭제", description = "폴더 ID와 사용자 UUID를 받아 해당 폴더를 삭제합니다.")
    public List<ResFolder> deleteFolder(
            @PathVariable Long folderId,
            @RequestBody ReqUuid request) {

        return folderService.deleteFolder(folderId, request);

    }

    @GetMapping("")
    @Operation(summary = "폴더 목록 조회", description = "사용자 UUID를 기반으로 전체 폴더 목록을 조회합니다.")
    public List<ResFolder> getFolderList(@RequestParam UUID uuid) {
        return folderService.getFoldersByUuid(uuid);
    }

    @GetMapping("/{folderId}")
    @Operation(summary = "특정 폴더 조회", description = "선택한 폴더의 정보와 폴더 내 대화 리스트를 반환합니다.")
    public ResFolderDetail getFolderDetail(@PathVariable Long folderId) {
        List<ResChat> chatList = List.of(
                new ResChat(1L, "마케팅 회의"),
                new ResChat(2L, "개발 피드백")
        );

        return new ResFolderDetail(folderId, "프로젝트 A", chatList);
    }

}
