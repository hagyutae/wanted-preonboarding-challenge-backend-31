package com.preonboarding.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 1000)
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    private String website;

    @OneToMany(mappedBy = "brand", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}