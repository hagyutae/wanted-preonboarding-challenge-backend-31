package com.example.demo.brand.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "brands")
@NoArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    String name;
    @Column(name = "slug", columnDefinition = "VARCHAR(100)", nullable = false, unique = true)
    String slug;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @Column(name = "logo_url", columnDefinition = "VARCHAR(255)")
    String logoUrl;
    @Column(name = "website", columnDefinition = "VARCHAR(255)")
    String website;
}
