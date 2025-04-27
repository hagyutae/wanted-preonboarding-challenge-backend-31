import { ApiPropertyOptional } from "@nestjs/swagger";

export default class ProductQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호 (기본값: 1)", example: 1 })
  page?: number = 1;

  @ApiPropertyOptional({ description: "페이지당 아이템 수 (기본값: 10)", example: 10 })
  perPage?: number = 10;

  @ApiPropertyOptional({
    description:
      "정렬 기준. 형식: {필드}:{asc|desc}. 여러 개인 경우 콤마로 구분 (기본값: created_at:desc)",
    example: "created_at:desc",
  })
  sort?: string = "created_at:desc";

  @ApiPropertyOptional({
    description: "상품 상태 필터 (ACTIVE, OUT_OF_STOCK, DELETED)",
    example: "ACTIVE",
  })
  status?: string;

  @ApiPropertyOptional({ description: "최소 가격 필터", example: 10000 })
  minPrice?: number;

  @ApiPropertyOptional({ description: "최대 가격 필터", example: 100000 })
  maxPrice?: number;

  @ApiPropertyOptional({
    description: "카테고리 ID 필터 (여러 개인 경우 콤마로 구분)",
    example: [5],
  })
  category?: number[];

  @ApiPropertyOptional({ description: "판매자 ID 필터", example: 1 })
  seller?: number;

  @ApiPropertyOptional({ description: "브랜드 ID 필터", example: 2 })
  brand?: number;

  @ApiPropertyOptional({ description: "재고 유무 필터", example: true })
  inStock?: boolean;

  @ApiPropertyOptional({ description: "검색어", example: "소파" })
  search?: string;
}
