package com.sandro.wanted_shop.seller;

import com.sandro.wanted_shop.common.entity.BaseTimeEntity;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "sellers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseTimeEntity {
    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    private String logoUrl;

    private BigDecimal rating;

    private String contactEmail;

    private String contactPhone;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @Builder
    public Seller(String name, String description, String logoUrl, BigDecimal rating, String contactEmail, String contactPhone, List<Product> products) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.products = Optional.ofNullable(products).orElse(new ArrayList<>());

        this.validate();
    }

    private void validate() {
        assert StringUtils.hasText(this.name)
                : "name is required";
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}