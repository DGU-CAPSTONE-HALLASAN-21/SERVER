package org.dgu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private String categoryPrompt;

    @OneToOne(mappedBy = "category")
    private Message message;

    @Builder
    public Category(String categoryName, String categoryPrompt, Message message) {
        this.categoryName = categoryName;
        this.categoryPrompt = categoryPrompt;
        this.message = message;
    }
}
