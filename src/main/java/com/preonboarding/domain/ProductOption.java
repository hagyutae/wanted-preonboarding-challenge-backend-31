package com.preonboarding.domain;

import com.preonboarding.dto.request.ProductOptionRequestDto;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup productOptionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    @Column(length = 100)
    private String sku;

    @Column
    private Integer stock;

    @Column(name = "display_order")
    private Integer displayOrder;

    public static ProductOption of(ProductOptionRequestDto dto) {
        if (dto.getStock() == 0) {
            throw new BaseException(false, ErrorResponseDto.of(ErrorCode.INVALID_STOCK_INPUT));
        }

        return ProductOption.builder()
                .name(dto.getName())
                .additionalPrice(dto.getAdditionalPrice())
                .sku(dto.getSku())
                .stock(dto.getStock())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }

    public void updateProductOptionGroup(ProductOptionGroup productOptionGroup) {
        if (this.productOptionGroup != null) {
            this.productOptionGroup.getProductOptionList().remove(this);
        }

        this.productOptionGroup = productOptionGroup;
        productOptionGroup.getProductOptionList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOption that = (ProductOption) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
