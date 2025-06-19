package org.dgu.controller;

import org.dgu.dto.file.FileInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {
    private final List<String> files = new ArrayList<>();
    private final List<String> fileContents = new ArrayList<>();
    private final List<Long> fileSizes = new ArrayList<>();

    @PostMapping("/upload")
    public ResponseEntity<String> handleMultipleFileUpload(@RequestParam("files") List<MultipartFile> fileList) {
        try {
            Path uploadDir = Paths.get("upload-dir");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            for (MultipartFile file : fileList) {
                String fileName = file.getOriginalFilename();
                long size = file.getSize();

                // 파일 저장
                Path filePath = uploadDir.resolve(fileName);
                Files.write(filePath, file.getBytes());

                // 메모리에 정보 저장
                files.add(fileName);
                fileContents.add(readCsvFileAsString(file));
                fileSizes.add(size);
            }

            return ResponseEntity.ok("Uploaded " + fileList.size() + " files successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload files: " + e.getMessage());
        }
    }

    public String readCsvFileAsString(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }

}
