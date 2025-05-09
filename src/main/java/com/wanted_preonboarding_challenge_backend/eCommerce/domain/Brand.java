package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "brand")
@Getter @Setter @NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String website;
}
