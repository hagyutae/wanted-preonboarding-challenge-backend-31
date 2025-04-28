package com.june.ecommerce.domain.category;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String slug;
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    private int level;
    private String imageUrl;

    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategories;
}
