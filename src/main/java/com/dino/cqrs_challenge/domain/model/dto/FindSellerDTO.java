package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

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

}
