package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductTag;
import com.dino.cqrs_challenge.domain.entity.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품 태그")
public class FindProductTagDTO {

    @Schema(description = "태그 식별 번호")
    private Long id;

    @Schema(description = "태그 이름")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    public static FindProductTagDTO from(ProductTag productTag) {
        FindProductTagDTO findProductTagDTO = new FindProductTagDTO();
        Tag tag = productTag.getTag();
        findProductTagDTO.id = tag.getId();
        findProductTagDTO.name = tag.getName();
        findProductTagDTO.slug = tag.getSlug();
        return findProductTagDTO;
    }
}
