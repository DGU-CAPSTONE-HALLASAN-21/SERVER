package org.dgu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dgu.dto.chat.ReqChatCreate;
import org.dgu.dto.chat.ReqChatRename;
import org.dgu.dto.chat.ResChat;
import org.dgu.dto.chat.ResChatDetail;
import org.dgu.dto.message.ResMessage;
import org.dgu.service.chat.ChatService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders/{folderId}/chats/{promptId}")
@Tag(name = "Chat", description = "폴더 내 대화 API")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    @Operation(summary = "대화 생성", description = "폴더 내 새로운 대화를 생성합니다. promptId 1은 최종 선정 프롬프트, 2는 롤플레잉 기법, 3은 롤플레잉+COT 기법, 4는 Few-shot 기법을 적용한 프롬프트입니다.")
    public String getAnswer(
            @PathVariable("folderId") Long folderId,
            @PathVariable("promptId") Long promptId,
            @RequestBody String content
    ) {
        return chatService.answer(promptId, content).choices().getFirst().message().content();
    }

    @GetMapping("")
    @Operation(summary = "대화 목록 조회", description = "폴더 내 모든 대화를 조회합니다.")
    public List<ResChat> getChatList(@PathVariable Long folderId) {
        return List.of();
    }

    @PatchMapping("/{chatId}")
    @Operation(summary = "대화 수정", description = "대화명을 수정합니다.")
    public List<ResChat> updateChatName(@PathVariable Long folderId, @PathVariable Long chatId, @RequestBody ReqChatRename request) {
        // 실제 서비스 없이 더미 응답 생성
        return List.of();
    }

    @DeleteMapping("/{chatId}")
    @Operation(summary = "대화 삭제", description = "대화 ID를 기반으로 해당 대화를 삭제합니다.")
    public List<ResChat> deleteChat(@PathVariable Long folderId, @PathVariable Long chatId) {
        // 실제 서비스 없이 더미 응답 생성
        return List.of();
    }

    @GetMapping("/{chatId}")
    @Operation(summary = "특정 대화 메시지 조회", description = "선택한 대화 안의 채팅 메시지들을 모두 조회합니다.")
    public ResChatDetail getChatDetail(
            @PathVariable Long folderId,
            @PathVariable Long chatId) {

        List<ResMessage> messages = List.of();

        return new ResChatDetail(chatId, "마케팅 회의", messages);
    }

}

