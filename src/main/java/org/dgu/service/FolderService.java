package org.dgu.service;

import lombok.RequiredArgsConstructor;
import org.dgu.dto.folder.ReqFolderCreate;
import org.dgu.dto.folder.ReqFolderRename;
import org.dgu.dto.folder.ReqUuid;
import org.dgu.dto.folder.ResFolder;
import org.dgu.entity.Folder;
import org.dgu.repository.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<ResFolder> createFolder(ReqFolderCreate request) {
        Folder folder = Folder.builder()
                .name(request.name()).uuid(request.uuid())
                .build();

        Folder saved = folderRepository.save(folder);

        // 폴더 저장 후 전체 목록 조회
        return folderRepository.findAllByUuid(request.uuid()).stream()
                .map(f -> new ResFolder(f.getId(), f.getName()))
                .toList();
    }

    public List<ResFolder> updateFolderName(Long folderId, ReqFolderRename request) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));

        folder.updateName(request.name());
        folderRepository.save(folder);

        // 수정 후, 해당 uuid에 속한 전체 폴더 목록 반환
        return folderRepository.findAllByUuid(request.uuid()).stream()
                .map(f -> new ResFolder(f.getId(), f.getName()))
                .toList();
    }

    public List<ResFolder> deleteFolder(Long id, ReqUuid request) {
        Folder folder = folderRepository.findById(id)
                .filter(f -> f.getUuid().equals(request.uuid()))
                .orElseThrow(() -> new RuntimeException("해당 UUID에 해당 폴더가 존재하지 않습니다."));

        folderRepository.delete(folder);

        List<Folder> folders = folderRepository.findAllByUuid(request.uuid());
        return folders.stream()
                .map(f -> new ResFolder(f.getId(), f.getName()))
                .toList();
    }

    public List<ResFolder> getFoldersByUuid(UUID uuid) {
        List<Folder> folders = folderRepository.findAllByUuid(uuid);
        return folders.stream()
                .map(f -> new ResFolder(f.getId(), f.getName()))
                .toList();
    }
}
