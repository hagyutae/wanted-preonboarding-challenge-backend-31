package com.challenge.onboarding.product.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tags")
@Getter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    protected Tag() {
    }
}
