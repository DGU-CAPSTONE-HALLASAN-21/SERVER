package org.dgu.service.chat;

import java.util.ArrayList;
import java.util.List;
import org.dgu.common.prompt.AutoCompletionPrompt;
import org.dgu.dto.autocomplete.ReqAutoComplete;
import org.dgu.dto.autocomplete.ResAutoComplete;
import org.dgu.dto.autocomplete.vo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {
    private final String model;
    private final String apiURL;
    private final RestTemplate template;

    public ChatService(
            @Value("${openai.model}") String model,
            @Value("${openai.api.url}") String apiURL,
            RestTemplate template
    ) {
        this.model = model;
        this.apiURL = apiURL;
        this.template = template;
    }

    //TODO: ~Auto~ 아래 클래스 명 변경
    public ResAutoComplete answer(final String content) {
        String promptWithInput = AutoCompletionPrompt.질문답변.getContent().replace("${input}", content);

        ReqAutoComplete request = ReqAutoComplete.builder()
                .model(model)
                .store(true)
                .temperature(1.3)
                .messages(new ArrayList<>(List.of(new Message("user", promptWithInput))))
                .build();
        return template.postForObject(apiURL, request, ResAutoComplete.class);
    }
}
