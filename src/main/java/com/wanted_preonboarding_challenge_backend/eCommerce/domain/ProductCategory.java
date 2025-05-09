package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "product_category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean isPrimary;
}