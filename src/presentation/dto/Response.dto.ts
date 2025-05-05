import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsArray, IsBoolean, IsOptional, ValidateNested } from "class-validator";

import { Category, Review } from "src/domain/entities";
import PaginationSummaryDTO from "./PaginationSummary.dto";
import ProductSummaryDTO from "./ProductSummary.dto";
import ReviewSummaryDTO from "./ReviewSummary.dto";

export default class ResponseDTO<T> {
  @ApiProperty({ description: "요청 성공 여부", example: true })
  @IsBoolean()
  success: boolean;

  @ApiProperty({ description: "응답 데이터", example: {} })
  @ValidateNested()
  data: T;

  @ApiPropertyOptional({
    description: "응답 메시지",
    example: "요청이 성공적으로 처리되었습니다.",
    required: false,
  })
  @IsOptional()
  message?: string;
}

export class ProductResponseBundle {
  @ApiProperty({ description: "상품 정보", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  items: ProductSummaryDTO[];

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @ValidateNested()
  pagination: PaginationSummaryDTO;
}

export class CategoryResponseBundle {
  @ApiProperty({ description: "카테고리 정보", type: Category })
  @ValidateNested()
  category: Category;

  @ApiProperty({ description: "상품 요약 목록", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  items: ProductSummaryDTO[];

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @ValidateNested()
  pagination: PaginationSummaryDTO;
}

export class ReviewResponseBundle {
  @ApiProperty({ description: "리뷰 목록", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  items: Review[];

  @ApiProperty({ description: "리뷰 요약 정보", type: ReviewSummaryDTO })
  @ValidateNested()
  summary: ReviewSummaryDTO;

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @ValidateNested()
  pagination: PaginationSummaryDTO;
}

export class MainResponseBundle {
  @ApiProperty({ description: "신상품 목록", type: [ProductSummaryDTO] })
  @IsArray()
  new_products: ProductSummaryDTO[];

  @ApiProperty({ description: "인기 상품 목록", type: [ProductSummaryDTO] })
  @IsArray()
  popular_products: ProductSummaryDTO[];

  @ApiProperty({ description: "추천 카테고리 목록", type: [Category] })
  @IsArray()
  featured_categories: Category[];
}
