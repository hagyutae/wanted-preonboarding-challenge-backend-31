package com.pawn.wantedcqrs.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    name: 카테고리명
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    slug: URL 슬러그
    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;

    //    description: 설명
    @Column(name = "description")
    private String description;

    //    parent_id: 상위 카테고리 ID (FK, 자기참조)
    @Column(name = "parent_id")
    private Long parentId; // TODO: 효율적인 방법 찾기

    //    level: 카테고리 레벨 (1: 대분류, 2: 중분류, 3: 소분류)
    @Column(name = "level")
    private Integer level; // TODO: ENUM으로 만들기

    //    image_url: 카테고리 이미지
    @Column(name = "image_url")
    private String imageUrl;

    public Category(Long id, String name, String slug, String description, Long parentId, Integer level, String imageUrl) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parentId = parentId;
        this.level = level;
        this.imageUrl = imageUrl;
    }

}
