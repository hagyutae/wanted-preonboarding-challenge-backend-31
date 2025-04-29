package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "brands")
public class Brand {
    // 브랜드 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 브랜드명
    @Column(length = 100, nullable = false)
    private String name;

    // URL 슬러그
    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    // 설명
    private String description;

    // 로고 이미지 URL
    @Column(length = 255)
    private String logoUrl;

    // 웹사이트 URL
    @Column(length = 255)
    private String website;
}
