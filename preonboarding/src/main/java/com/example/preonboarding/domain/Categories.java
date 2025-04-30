package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Long parentId;
    private int level;
    private String imageUrl;

    @OneToMany(mappedBy = "categories")
    private List<ProductCategories> productCategories = new ArrayList<>();
}
