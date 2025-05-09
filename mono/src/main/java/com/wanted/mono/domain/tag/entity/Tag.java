package com.wanted.mono.domain.tag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tags")
public class Tag {

    // 태그 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 태그명
    @Column(length = 100, nullable = false)
    private String name;

    // URL 슬러그
    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    // 상품 태그들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductTag> productTags = new ArrayList<>();
}
