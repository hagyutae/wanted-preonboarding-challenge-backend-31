package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_images")
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    url: 이미지 URL
    @Column(name = "url", nullable = false)
    private String url;

    //    alt_text: 대체 텍스트
    @Column(name = "alt_text")
    private String altText;

    //    is_primary: 대표 이미지 여부
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    //    display_order: 표시 순서
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    //    product_id: 상품 ID (FK)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    //    option_id: 연관된 옵션 ID (FK, nullable)
    @Column(name = "option_id", nullable = false)
    private Long optionId;

    public ProductImage(Long id, String url, String altText, Boolean isPrimary, Integer displayOrder, Long productId, Long optionId) {
        this.id = id;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.productId = productId;
        this.optionId = optionId;
    }

}
