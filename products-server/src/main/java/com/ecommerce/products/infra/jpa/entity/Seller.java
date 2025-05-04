package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Comment("판매자")
@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Comment("판매자 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("판매자명")
    @Column(nullable = false)
    private String name;

    @Comment("설명")
    @Column(length = 1000)
    private String description;

    @Comment("로고 이미지 URL")
    @Column(name = "logo_url")
    private String logoUrl;

    @Comment("평점")
    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Comment("연락처 이메일")
    @Column(name = "contact_email")
    private String contactEmail;

    @Comment("연락처 전화번호")
    @Column(name = "contact_phone")
    private String contactPhone;

    @Comment("등록일")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "seller", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}