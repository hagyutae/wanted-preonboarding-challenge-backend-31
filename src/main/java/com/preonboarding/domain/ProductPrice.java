package com.preonboarding.domain;

import com.preonboarding.dto.request.product.ProductPriceRequestDto;
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
@Table(name = "product_prices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(length = 3)
    private String currency;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    public static ProductPrice of(ProductPriceRequestDto dto) {
        if (dto.getBasePrice().equals(BigDecimal.ZERO)) {
            throw new BaseException(false, ErrorResponseDto.of(ErrorCode.INVALID_PRODUCT_BASE_PRICE_INPUT));
        }

        if (dto.getSalePrice().compareTo(dto.getCostPrice()) < 0) {
            throw new BaseException(false,ErrorResponseDto.of(ErrorCode.INVALID_PRODUCT_SALE_PRICE_INPUT));
        }

        return ProductPrice.builder()
                .basePrice(dto.getBasePrice())
                .salePrice(dto.getSalePrice())
                .costPrice(dto.getCostPrice())
                .currency(dto.getCurrency())
                .taxRate(dto.getTaxRate())
                .build();
    }

    public void updateProduct(Product product) {
        this.product = product;
        product.updateProductPrice(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
