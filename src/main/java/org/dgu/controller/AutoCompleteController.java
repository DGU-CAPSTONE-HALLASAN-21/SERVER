package org.dgu.controller;

import lombok.RequiredArgsConstructor;
import org.dgu.dto.autocomplete.ResAutoComplete;
import org.dgu.service.autocomplete.AutocompleteService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auto")
public class AutoCompleteController {
    private final AutocompleteService autocompleteService;

    @PostMapping
    String predictFullSentence(@RequestBody String input) {
        return autocompleteService.predictFullSentence(input).choices().get(0).message().content().substring(input.length());
    }
}
