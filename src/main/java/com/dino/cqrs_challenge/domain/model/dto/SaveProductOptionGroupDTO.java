package com.dino.cqrs_challenge.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "옵션 그룹")
public class SaveProductOptionGroupDTO {
    @Schema(description = "옵션 그룹명")
    public String name;

    @Schema(description = "표시 순서")
    public Integer displayOrder;

    @Schema(description = "옵션 목록")
    public List<SaveProductOptionDTO> options = new ArrayList<>();
}
