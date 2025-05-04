package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Comment("카테고리 Table")
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Comment("Category ID")
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("카테고리명")
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Comment("URL 슬러그")
    @NotNull
    @Column(name = "slug", nullable = false, length = 100, unique = true)
    private String slug;

    @Comment("설명")
    @Lob
    @Column(name = "description")
    private String description;

    @Comment("parent 카테고리")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Category> children = new ArrayList<>();

    @Comment("카테고리 레벨 (1: 대분류, 2: 중분류, 3: 소분류)")
    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @Comment("카테고리 이미지")
    @Column(name = "image_url")
    private String imageUrl;

    public void addChild(Category child) {
        child.setParent(this);
        child.setLevel(this.level + 1);
        children.add(child);
    }
}