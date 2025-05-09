package com.wanted.mono.domain.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 (Category)
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "categories")
public class Category {

    // 카테고리 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카테고리명
    @Column(length = 100, nullable = false)
    private String name;

    // URL 슬러그
    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    // 설명
    private String description;

    // 상위 카테고리 ID (자기참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 카테고리 레벨 (1: 대분류, 2: 중분류, 3: 소분류)
    @Column(nullable = false)
    private Integer level;

    // 카테고리 이미지 URL
    @Column(length = 255)
    private String imageUrl;

    // 상품 카테고리들
    // 카테고리 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();
}

