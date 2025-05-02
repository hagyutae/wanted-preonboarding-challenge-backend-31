import { ApiPropertyOptional } from "@nestjs/swagger";
import { Transform } from "class-transformer";
import { IsArray, IsBoolean, IsInt, IsOptional, Matches } from "class-validator";

const SORT_REGEX = /^(\w+:(asc|desc))(,\w+:(asc|desc))*$/;

export default class ProductQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호 (기본값: 1)", example: 1, required: false })
  @IsOptional()
  @IsInt()
  page?: number = 1;

  @ApiPropertyOptional({
    description: "페이지당 아이템 수 (기본값: 10)",
    example: 10,
    required: false,
  })
  @IsOptional()
  @IsInt()
  perPage?: number = 10;

  @ApiPropertyOptional({
    description:
      "정렬 기준. 형식: {필드}:{asc|desc}. 여러 개인 경우 콤마로 구분 (기본값: created_at:desc)",
    example: "created_at:desc",
    required: false,
  })
  @IsOptional()
  @Matches(SORT_REGEX, {
    message: "sort 형식은 field:asc|desc (복수는 콤마로 구분) 이어야 합니다.",
  })
  sort?: string = "created_at:desc";

  @ApiPropertyOptional({
    description: "상품 상태 필터 (ACTIVE, OUT_OF_STOCK, DELETED)",
    example: "ACTIVE",
    required: false,
  })
  @IsOptional()
  status?: string;

  @ApiPropertyOptional({ description: "최소 가격 필터", example: 10000, required: false })
  @IsOptional()
  @IsInt()
  minPrice?: number;

  @ApiPropertyOptional({ description: "최대 가격 필터", example: 100000, required: false })
  @IsOptional()
  @IsInt()
  maxPrice?: number;

  @ApiPropertyOptional({
    description: "카테고리 ID 필터 (여러 개인 경우 콤마로 구분)",
    example: [5],
    type: [Number],
    required: false,
  })
  @IsOptional()
  @IsArray()
  @IsInt({ each: true })
  @Transform(({ value }) => (typeof value === "string" ? value.split(",") : value).map(Number))
  category?: number[];

  @ApiPropertyOptional({ description: "판매자 ID 필터", example: 1, required: false })
  @IsOptional()
  @IsInt()
  seller?: number;

  @ApiPropertyOptional({ description: "브랜드 ID 필터", example: 2, required: false })
  @IsOptional()
  @IsInt()
  brand?: number;

  @ApiPropertyOptional({ description: "재고 유무 필터", example: true, required: false })
  @IsOptional()
  @IsBoolean()
  inStock?: boolean;

  @ApiPropertyOptional({ description: "검색어", example: "소파", required: false })
  @IsOptional()
  search?: string;
}
