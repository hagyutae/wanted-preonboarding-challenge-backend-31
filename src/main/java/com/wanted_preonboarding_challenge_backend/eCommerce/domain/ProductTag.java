package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_tag")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
