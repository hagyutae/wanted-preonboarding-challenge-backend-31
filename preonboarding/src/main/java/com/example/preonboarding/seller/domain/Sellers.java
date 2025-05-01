package com.example.preonboarding.seller.domain;

import com.example.preonboarding.products.domain.Products;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sellers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "sellers")
    private List<Products> products = new ArrayList<>();

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
