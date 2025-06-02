package org.dgu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean isUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Session session;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Message(String message, Boolean isUser, Session session) {
        this.message = message;
        this.isUser = isUser;
        this.session = session;
    }
}
