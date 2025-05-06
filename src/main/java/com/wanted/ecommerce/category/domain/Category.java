package com.wanted.ecommerce.category.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    private Integer level;
    private String imageUrl;

    public static Category of(String name, String slug, String description, Category parent,
        Integer level, String imageUrl){
        return Category.builder()
            .name(name)
            .slug(slug)
            .description(description)
            .parent(parent)
            .level(level)
            .imageUrl(imageUrl)
            .build();
    }

    public void update(String name, String slug, String description, Category parent,
        Integer level, String imageUrl){
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.level = level;
        this.imageUrl = imageUrl;
    }
}
