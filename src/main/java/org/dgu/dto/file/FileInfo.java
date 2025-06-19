package org.dgu.dto.file;

import lombok.Getter;

@Getter
public class FileInfo {
    private String fileName;
    private String content;
    private Long fileSize;

    public FileInfo(String fileName, String content, Long fileSize) {
        this.fileName = fileName;
        this.content = content;
        this.fileSize = fileSize;
    }

}
