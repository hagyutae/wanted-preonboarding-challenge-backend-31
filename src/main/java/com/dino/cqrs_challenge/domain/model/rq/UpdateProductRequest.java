package com.dino.cqrs_challenge.domain.model.rq;


import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import com.dino.cqrs_challenge.domain.model.dto.SaveProductCategoryDTO;
import com.dino.cqrs_challenge.domain.model.dto.SaveProductDetailDTO;
import com.dino.cqrs_challenge.domain.model.dto.SaveProductImageDTO;
import com.dino.cqrs_challenge.domain.model.dto.SaveProductOptionGroupDTO;
import com.dino.cqrs_challenge.domain.model.dto.SaveProductPriceDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "상품 수정 요청 DTO")
public class UpdateProductRequest {

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그 (SEO friendly ID)")
    private String slug;

    @Schema(description = "짧은 설명")
    private String shortDescription;

    @Schema(description = "전체 설명")
    private String fullDescription;

    @Schema(description = "판매자 ID")
    private Long sellerId;

    @Schema(description = "브랜드 ID")
    private Long brandId;

    @Schema(description = "상품 상태")
    private ProductStatus status;

    @Schema(description = "상품 상세 정보")
    private SaveProductDetailDTO detail;

    @Schema(description = "가격 정보")
    private SaveProductPriceDTO price;

    @Schema(description = "카테고리 목록")
    private List<SaveProductCategoryDTO> categories = new ArrayList<>();

    @Schema(description = "옵션 그룹 목록")
    private List<SaveProductOptionGroupDTO> optionGroups = new ArrayList<>();

    @Schema(description = "이미지 목록")
    private List<SaveProductImageDTO> images = new ArrayList<>();

    @Schema(description = "태그 ID 목록")
    private List<Long> tags = new ArrayList<>();

}
