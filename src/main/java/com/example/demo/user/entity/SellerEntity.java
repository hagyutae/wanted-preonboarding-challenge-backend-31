package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "sellers")
@NoArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    String name;
    @Column(name = "description", columnDefinition = "TEXT")
    String description;
    @Column(name = "logo_url", columnDefinition = "VARCHAR(255)")
    String logoUrl;
    @Column(name = "rating", columnDefinition = "DECIMAL(3,2)")
    BigDecimal rating;
    @Column(name = "contact_email", columnDefinition = "VARCHAR(100)")
    String contactEmail;
    @Column(name = "contact_phone", columnDefinition = "VARCHAR(20)")
    String contactPhone;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdAt;
}
