package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product {
    // 상품 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품명
    @Column(length = 255, nullable = false)
    private String name;

    // URL 슬러그 (SEO 최적화용)
    @Column(length = 255, unique = true, nullable = false)
    private String slug;

    // 짧은 설명
    @Column(length = 500)
    private String shortDescription;

    // 전체 설명 (HTML 허용)
    private String fullDescription;

    // 등록일
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // 수정일
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // 판매자 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    // 브랜드 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // 상태 (판매중, 품절, 삭제됨 등)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // 상품 상세 설명들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductDetail> productDetails = new ArrayList<>();

    // 상품 가격들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductPrice> productPrices = new ArrayList<>();

    // 상품 카테고리들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    // 상품 옵션 그룹
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    // 상품 이미지들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    // 상품 태그들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductTag> productTags = new ArrayList<>();

    // 상품 리뷰들
    // 상품 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
