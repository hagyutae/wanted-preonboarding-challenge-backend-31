package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seller")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;
    private LocalDateTime createdAt;
}
