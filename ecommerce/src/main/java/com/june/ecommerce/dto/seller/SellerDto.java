package com.june.ecommerce.dto.seller;

import com.june.ecommerce.domain.seller.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {
    private int sellerId;
    private String sellerName;
    private String contactEmail;

    public static SellerDto fromEntity(Seller entity) {
        if (entity == null) return null;
        return SellerDto.builder()
                .sellerId(entity.getId())
                .sellerName(entity.getName())
                .contactEmail(entity.getContactEmail())
                .build();
    }
}