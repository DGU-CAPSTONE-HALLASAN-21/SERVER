package org.dgu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OracleAiController {

    private final OracleAiService oracleAiService;

    @GetMapping("/generate")
    public ResponseEntity<String> generate(@RequestParam(defaultValue = "Hello, who are you?") String input) throws Exception {
        String result = oracleAiService.generateText(input);
        return ResponseEntity.ok(result);
    }
}
