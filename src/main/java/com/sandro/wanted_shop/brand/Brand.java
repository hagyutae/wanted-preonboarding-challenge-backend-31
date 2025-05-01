package com.sandro.wanted_shop.brand;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "brands")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    private String description;

    private String logoUrl;

    private String website;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @Builder
    public Brand(String name, String slug, String description, String logoUrl, String website, List<Product> products) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.logoUrl = logoUrl;
        this.website = website;
        this.products = Optional.ofNullable(products).orElse(new ArrayList<>());

        validate();
    }

    private void validate() {
        assert StringUtils.hasText(this.name)
                && StringUtils.hasText(this.slug)
                : "Brand name and slug must not be empty";
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}