package com.sandro.wanted_shop.tag;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.relation.ProductTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductTag> productTags;

    @Builder
    public Tag(String name, String slug, List<ProductTag> productTags) {
        this.name = name;
        this.slug = slug;
        this.productTags = Optional.ofNullable(productTags).orElse(new ArrayList<>());

        validate();
    }

    private void validate() {
        assert StringUtils.hasText(this.name)
                && StringUtils.hasText(this.slug)
                : "name and slug are required";
    }

    public void addProductTag(ProductTag productTag) {
        this.productTags.add(productTag);
    }
}