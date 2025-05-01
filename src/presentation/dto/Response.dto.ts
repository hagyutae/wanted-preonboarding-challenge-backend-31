import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsArray, IsBoolean, IsOptional } from "class-validator";

import { Category, Product_Summary, Review } from "src/domain/entities";
import PaginationSummaryDTO from "./PaginationSummary.dto";
import ReviewSummaryDTO from "./ReviewSummary.dto";

export default class ResponseDTO<T> {
  @ApiProperty({ description: "요청 성공 여부", example: true })
  @IsBoolean()
  success: boolean;

  @ApiProperty({ description: "응답 데이터", example: {} })
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
  @ApiProperty({ type: () => Product_Summary, description: "상품 정보" })
  items: Product_Summary[];

  @ApiProperty({ description: "페이지네이션 정보" })
  pagination: PaginationSummaryDTO;
}

export class CategoryResponseBundle {
  @ApiProperty({ description: "카테고리 정보" })
  category: Category;

  @ApiProperty({ type: () => [Product_Summary], description: "상품 요약 목록" })
  @IsArray()
  items: Product_Summary[];

  @ApiProperty({ description: "페이지네이션 정보" })
  pagination: PaginationSummaryDTO;
}

export class ReviewResponseBundle {
  @ApiProperty({ type: () => [Product_Summary], description: "리뷰 목록" })
  @IsArray()
  items: Review[];

  @ApiProperty({ description: "리뷰 요약 정보" })
  summary: ReviewSummaryDTO;

  @ApiProperty({ description: "페이지네이션 정보" })
  pagination: PaginationSummaryDTO;
}

export class MainResponseBundle {
  @ApiProperty({ type: () => [Product_Summary], description: "신상품 목록" })
  new_products: Product_Summary[];

  @ApiProperty({ type: () => [Product_Summary], description: "인기 상품 목록" })
  popular_products: Product_Summary[];

  @ApiProperty({ type: () => [Category], description: "추천 카테고리 목록" })
  featured_categories: Category[];
}
