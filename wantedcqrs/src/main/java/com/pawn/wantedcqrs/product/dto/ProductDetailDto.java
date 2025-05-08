package com.pawn.wantedcqrs.product.dto;

import com.pawn.wantedcqrs.product.entity.Product;
import com.pawn.wantedcqrs.product.entity.ProductDetail;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailDto {

    private Long id;

    private Long productId;

    private BigDecimal weight;

    private ProductDetailDto.Dimensions dimensions;

    private String materials;

    private String countryOfOrigin;

    private String warrantyInfo;

    private String careInstructions;

    private ProductDetailDto.AdditionalInfo additionalInfo;

    @Data
    public static class Dimensions {

        private int width;

        private int height;

        private int depth;

        public Dimensions(int width, int height, int depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

    }

    @Data
    public static class AdditionalInfo {

        private boolean assemblyRequired;

        private String assemblyTime;

        public AdditionalInfo(boolean assemblyRequired, String assemblyTime) {
            this.assemblyRequired = assemblyRequired;
            this.assemblyTime = assemblyTime;
        }

    }

    public ProductDetail toEntity() {
        return ProductDetail.builder()
                .id(id)
//                .product(product)
                .weight(weight)
                .dimensions(ProductDetail.Dimensions.builder()
                        .depth(dimensions.depth)
                        .width(dimensions.width)
                        .height(dimensions.height)
                        .build()
                ).materials(materials)
                .countryOfOrigin(countryOfOrigin)
                .warrantyInfo(warrantyInfo)
                .careInstructions(careInstructions)
                .additionalInfo(ProductDetail.AdditionalInfo.builder()
                        .assemblyRequired(additionalInfo.assemblyRequired)
                        .assemblyTime(additionalInfo.assemblyTime)
                        .build())
                .build();
    }

}
