package com.sandro.wanted_shop.domain;

import com.sandro.wanted_shop.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private List<Product> products = new ArrayList<>();
} 