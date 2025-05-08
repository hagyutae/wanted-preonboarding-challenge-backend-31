package com.example.demo.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;
    @Column(name = "slug", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    private String slug;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "level", columnDefinition = "INTEGER", nullable = false)
    private Integer level;
    @Column(name = "image_url", columnDefinition = "VARCHAR(255)")
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;
}
