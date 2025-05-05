import { ApiPropertyOptional } from "@nestjs/swagger";
import { Transform } from "class-transformer";
import { IsArray, IsBoolean, IsIn, IsInt, IsOptional, Matches, Min } from "class-validator";

const SORT_REGEX = /^(\w+:(asc|desc))(,\w+:(asc|desc))*$/;

export default class ProductQueryDTO {
  @ApiPropertyOptional({ description: "페이지 번호 (기본값: 1)", example: 1 })
  @IsOptional()
  @IsInt()
  @Min(1)
  page?: number = 1;

  @ApiPropertyOptional({ description: "페이지당 아이템 수 (기본값: 10)", example: 10 })
  @IsOptional()
  @IsInt()
  @Min(1)
  perPage?: number = 10;

  @ApiPropertyOptional({
    description:
      "정렬 기준. 형식: {필드}:{asc|desc}. 여러 개인 경우 콤마로 구분 (기본값: created_at:desc)",
    example: "created_at:desc",
  })
  @IsOptional()
  @Matches(SORT_REGEX, {
    message: "sort 형식은 field:asc|desc (복수는 콤마로 구분) 이어야 합니다.",
  })
  sort?: string = "created_at:desc";

  @ApiPropertyOptional({
    description: "상품 상태 필터 (ACTIVE, OUT_OF_STOCK, DELETED)",
    example: "ACTIVE",
  })
  @IsOptional()
  @IsIn(["ACTIVE", "OUT_OF_STOCK", "DELETED"], {
    message: "status는 ACTIVE, OUT_OF_STOCK 또는 DELETED만 허용됩니다.",
  })
  status?: string;

  @ApiPropertyOptional({ description: "최소 가격 필터", example: 10000 })
  @IsOptional()
  @IsInt()
  @Min(0)
  minPrice?: number;

  @ApiPropertyOptional({ description: "최대 가격 필터", example: 100000 })
  @IsOptional()
  @IsInt()
  @Min(0)
  maxPrice?: number;

  @ApiPropertyOptional({
    description: "카테고리 ID 필터 (여러 개인 경우 콤마로 구분)",
    example: [5],
    type: [Number],
  })
  @IsOptional()
  @IsArray()
  @IsInt({ each: true })
  @Transform(({ value }) => (typeof value === "string" ? value.split(",") : value).map(Number))
  category?: number[];

  @ApiPropertyOptional({ description: "판매자 ID 필터", example: 1 })
  @IsOptional()
  @IsInt()
  seller?: number;

  @ApiPropertyOptional({ description: "브랜드 ID 필터", example: 2 })
  @IsOptional()
  @IsInt()
  brand?: number;

  @ApiPropertyOptional({ description: "재고 유무 필터", example: true })
  @IsOptional()
  @IsBoolean()
  inStock?: boolean;

  @ApiPropertyOptional({ description: "검색어", example: "소파" })
  @IsOptional()
  search?: string;
}
