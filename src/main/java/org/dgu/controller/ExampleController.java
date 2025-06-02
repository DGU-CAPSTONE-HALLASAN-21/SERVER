package org.dgu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping("/example")
    @Operation(description = "예시임둥",summary = "대가리 바로 옆")
    void example(ExampleResponse count) {

    }
}
