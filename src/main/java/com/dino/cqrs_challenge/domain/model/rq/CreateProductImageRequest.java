package com.dino.cqrs_challenge.domain.model.rq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 이미지 추가 요청 DTO")
public class CreateProductImageRequest {

    @Schema(description = "이미지 URL")
    private String url;

    @Schema(description = "이미지 설명")
    private String altText;

    @Schema(description = "대표 이미지 여부")
    private Boolean isPrimary;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    @Schema(description = "옵션 ID (해당 이미지가 특정 옵션에 속하는 경우)")
    private Long optionId;
}
