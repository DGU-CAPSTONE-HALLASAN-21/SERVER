package org.dgu.repository;

import org.dgu.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUuid(UUID uuid);
}

