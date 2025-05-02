import { ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsInt, IsOptional, Matches } from "class-validator";

const SORT_REGEX = /^(\w+:(asc|desc))*$/;

export default class CategoryQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호", example: 1, required: false })
  @IsOptional()
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
  @Matches(SORT_REGEX, {
    message: "sort 형식은 field:asc|desc이어야 합니다.",
  })
  sort?: string = "created_at:desc";

  @ApiPropertyOptional({ description: "하위 카테고리 포함 여부", example: true, required: false })
  @IsOptional()
  @IsBoolean()
  includeSubcategories?: boolean;
}
