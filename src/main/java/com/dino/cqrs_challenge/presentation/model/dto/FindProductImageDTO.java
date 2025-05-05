package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 이미지")
public class FindProductImageDTO {

    @Schema(description = "이미지 식별 번호")
    private Long id;

    @Schema(description = "이미지 URL")
    private String url;

    @Schema(description = "대체 텍스트")
    private String altText;

    @Schema(description = "메인 이미지 여부")
    private Boolean isPrimary;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    @Schema(description = "옵션 식별 번호")
    private Long optionId;

    public static FindProductImageDTO from(ProductImage productImage) {
        FindProductImageDTO findProductImageDTO = new FindProductImageDTO();
        findProductImageDTO.id = productImage.getId();
        findProductImageDTO.url = productImage.getUrl();
        findProductImageDTO.altText = productImage.getAltText();
        findProductImageDTO.isPrimary = productImage.getIsPrimary();
        findProductImageDTO.displayOrder = productImage.getDisplayOrder();
        if (productImage.getOption() != null) {
            findProductImageDTO.optionId = productImage.getOption().getId();
        }
        return findProductImageDTO;
    }
}
