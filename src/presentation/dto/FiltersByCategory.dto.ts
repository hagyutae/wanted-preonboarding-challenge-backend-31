import { ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsInt, IsOptional } from "class-validator";

import { BooleanString } from "../decorators";

export default class FiltersByCategoryDTO {
  @ApiPropertyOptional({ description: "페이지 번호", example: 1, required: false })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  page?: number;

  @ApiPropertyOptional({ description: "페이지당 아이템 수", example: 10, required: false })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  perPage?: number;

  @ApiPropertyOptional({
    description: "정렬 기준. 형식: {필드}:{asc|desc}",
    example: "created_at:desc",
    required: false,
  })
  @IsOptional()
  sort?: string;

  @ApiPropertyOptional({ description: "하위 카테고리 포함 여부", example: true, required: false })
  @IsOptional()
  @BooleanString()
  @IsBoolean()
  includeSubcategories?: boolean;
}
