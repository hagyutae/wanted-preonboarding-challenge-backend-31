package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_option_groups")
public class ProductOptionGroup {

    // 옵션 그룹 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 옵션 그룹명 (예: "색상", "사이즈")
    @Column(length = 100, nullable = false)
    private String name;

    // 표시 순서
    private Integer displayOrder = 0;

    // 상품 옵션들
    // 옵션 삭제 시 같이 삭제됨
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();
}