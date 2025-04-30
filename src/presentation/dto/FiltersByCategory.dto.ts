import { ApiPropertyOptional } from "@nestjs/swagger";

export default class FiltersByCategoryDTO {
  @ApiPropertyOptional({ description: "페이지 번호", example: 1 })
  page?: number;

  @ApiPropertyOptional({ description: "페이지당 아이템 수", example: 10 })
  perPage?: number;

  @ApiPropertyOptional({
    description: "정렬 기준. 형식: {필드}:{asc|desc}",
    example: "created_at:desc",
  })
  sort?: string;

  @ApiPropertyOptional({ description: "하위 카테고리 포함 여부", example: true })
  includeSubcategories?: boolean;
}
