package com.sandro.wanted_shop.category;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.relation.ProductCategory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children;

    @Column(nullable = false)
    private Integer level; // 1 ~ 3

    private String imageUrl;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductCategory> productCategories;

    @Builder
    public Category(String name, String slug, String description, Category parent, List<Category> children,
                    Integer level, String imageUrl, List<ProductCategory> productCategories) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.children = Optional.ofNullable(children).orElse(new ArrayList<>());
        this.level = level;
        this.imageUrl = imageUrl;
        this.productCategories = Optional.ofNullable(productCategories).orElse(new ArrayList<>());

        validate();

        if (parent != null)
            parent.addChildren(this);
    }

    private void addChildren(Category category) {
        this.children.add(category);
    }

    private void validate() {
        assert StringUtils.hasText(this.name)
                && StringUtils.hasText(this.slug)
                && this.level != null
                : "name, slug and level are required";

        assert this.level >= 1 && this.level <= 3
                : "level must be between 1 and 3";
    }

    public void addProductCategory(ProductCategory productCategory) {
        this.productCategories.add(productCategory);
    }

    public void addChild(Category child) {
        this.children.add(child);
    }
}