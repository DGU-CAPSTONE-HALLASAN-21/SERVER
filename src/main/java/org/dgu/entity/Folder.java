package org.dgu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private UUID uuid;

    @Builder
    public Folder(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
