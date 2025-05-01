package com.example.preonboarding.brands.domain;

import com.example.preonboarding.products.domain.Products;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Brands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logo_url;
    private String website;

    @OneToMany(mappedBy = "brands")
    private List<Products> products = new ArrayList<>();


    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
