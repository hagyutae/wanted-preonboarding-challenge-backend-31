package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Seller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "판매자 정보 DTO")
public class FindSellerDTO {

    @Schema(description = "판매자 식별번호")
    private Long id;

    @Schema(description = "판매자명")
    private String name;

    @Schema(description = "판매자 설명")
    private String description;

    @Schema(description = "로고 URL")
    private String logoUrl;

    @Schema(description = "평점")
    private BigDecimal rating;

    @Schema(description = "판매자 연락처 이메일")
    private String contactEmail;

    @Schema(description = "판매자 연락처 전화번호")
    private String contactPhone;

    public static FindSellerDTO from(Seller seller) {
        FindSellerDTO findSellerDTO = new FindSellerDTO();
        findSellerDTO.id = seller.getId();
        findSellerDTO.name = seller.getName();
        findSellerDTO.description = seller.getDescription();
        findSellerDTO.logoUrl = seller.getLogoUrl();
        findSellerDTO.rating = seller.getRating();
        findSellerDTO.contactEmail = seller.getContactEmail();
        findSellerDTO.contactPhone = seller.getContactPhone();
        return findSellerDTO;
    }
}
