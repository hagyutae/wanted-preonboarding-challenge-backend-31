package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("상품 이미지")
@Entity
@Table(name = "product_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Comment("상품 이미지 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("상품 ID(FK)")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Comment("이미지 URL")
    @Column(nullable = false)
    private String url;

    @Comment("대체 텍스트")
    @Column(name = "alt_text")
    private String altText;

    @Comment("대표 이미지 여부")
    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Comment("표시 순서")
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Comment("연관된 옵션 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;

}