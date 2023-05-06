package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "saved-article")
@Entity
public class SavedArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "profile_id")
    @JoinColumn(name = "profile_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private ProfileEntity profileId;
    @Column(name = "article_id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity articleEntity;
}
