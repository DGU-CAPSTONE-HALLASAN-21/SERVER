package org.dgu.service.autocomplete;

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
public class AutocompleteService {
    private final String model;
    private final String apiURL;
    private final RestTemplate template;

    public AutocompleteService(
            @Value("${openai.model}") String model,
            @Value("${openai.api.url}") String apiURL,
            RestTemplate template
    ) {
        this.model = model;
        this.apiURL = apiURL;
        this.template = template;
    }

    public ResAutoComplete predictFullSentence(final String input) {
        String promptWithInput = AutoCompletionPrompt.자동완성.getContent().replace("${input}", input);
        ReqAutoComplete request = ReqAutoComplete.builder()
                .model(model)
                .store(true)
                .messages(new ArrayList<>(List.of(new Message("user", promptWithInput))))
                .build();
        return template.postForObject(apiURL, request, ResAutoComplete.class);
    }
}
