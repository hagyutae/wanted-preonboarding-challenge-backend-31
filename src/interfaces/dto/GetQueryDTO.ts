import { ApiPropertyOptional } from "@nestjs/swagger";

export default class GetQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호", example: 1 })
  page?: number;

  @ApiPropertyOptional({ description: "페이지 당 항목 수", example: 10 })
  perPage?: number;

  @ApiPropertyOptional({ description: "정렬 기준", example: "name" })
  sort?: string;

  @ApiPropertyOptional({ description: "상태 필터", example: "active" })
  status?: string;

  @ApiPropertyOptional({ description: "최소 가격", example: 1000 })
  minPrice?: number;

  @ApiPropertyOptional({ description: "최대 가격", example: 5000 })
  maxPrice?: number;

  @ApiPropertyOptional({ description: "카테고리 ID", example: 3 })
  category?: number;

  @ApiPropertyOptional({ description: "판매자 ID", example: 2 })
  seller?: number;

  @ApiPropertyOptional({ description: "브랜드 ID", example: 5 })
  brand?: number;

  @ApiPropertyOptional({ description: "재고 여부", example: true })
  inStock?: boolean;

  @ApiPropertyOptional({ description: "검색어", example: "laptop" })
  search?: string;
}
