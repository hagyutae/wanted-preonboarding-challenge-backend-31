package com.preonboarding.challenge.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Category> children = new ArrayList<>();

    @Column(nullable = false)
    private Integer level; // 1: 대분류, 2: 중분류, 3: 소분류

    @Column(name = "image_url")
    private String imageUrl;

    // Product 참조를 제거함

    // Helper method
    public void addChild(Category child) {
        child.setParent(this);
        child.setLevel(this.level + 1);
        children.add(child);
    }
}