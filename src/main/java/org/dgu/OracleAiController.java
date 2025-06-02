package org.dgu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OracleAiController {

    private final OracleAiService oracleAiService;

    @GetMapping("/generate")
    public ResponseEntity<String> generate(@RequestParam(defaultValue = "Hello, who are you?") String input) throws Exception {
        String result = String.valueOf(oracleAiService.generateText(input));
        return ResponseEntity.ok(result);
    }
}
