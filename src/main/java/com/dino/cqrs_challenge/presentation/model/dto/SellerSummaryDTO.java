package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "판매자 요약 정보 DTO")
public class SellerSummaryDTO {

    @Schema(description = "판매자 식별번호")
    private Long id;

    @Schema(description = "판매자명")
    private String name;

    public static SellerSummaryDTO from(Seller seller) {
        if (seller == null) {
            return null;
        }
        SellerSummaryDTO dto = new SellerSummaryDTO();
        dto.id = seller.getId();
        dto.name = seller.getName();
        return dto;
    }
}
