package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "옵션 그룹 DTO")
public class FindProductOptionGroupDTO {
    @Schema(description = "옵션 그룹 식별 번호")
    private Long id;

    @Schema(description = "옵션 그룹 이름")
    private String name;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    @Schema(description = "옵션 목록")
    private List<FindProductOptionDTO> options = new ArrayList<>();
}
