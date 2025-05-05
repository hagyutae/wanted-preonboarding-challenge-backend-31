import { ApiProperty } from "@nestjs/swagger";

import { IsBoolean, IsDate, IsIn, IsInt, IsNumber, Matches, Max, Min } from "class-validator";

export default class ProductSummaryDTO {
  @ApiProperty({ description: "상품 ID", example: 123 })
  @IsInt()
  id: number;

  @ApiProperty({ description: "상품 이름", example: "슈퍼 편안한 소파" })
  name: string;

  @ApiProperty({ description: "슬러그", example: "super-comfortable-sofa" })
  slug: string;

  @ApiProperty({ description: "짧은 설명", example: "최고급 소재로 만든 편안한 소파" })
  short_description: string;

  @ApiProperty({ description: "기본 가격", example: 599000 })
  @IsInt()
  @Min(0)
  base_price: number;

  @ApiProperty({ description: "할인 가격", example: 499000 })
  @IsInt()
  @Min(0)
  sale_price: number;

  @ApiProperty({ description: "통화", example: "KRW" })
  @Matches(/^[A-Z]{3}$/, { message: "통화 코드는 3자리 대문자여야 합니다 (예: USD, KRW)" })
  currency: string;

  @ApiProperty({
    description: "주 이미지",
    example: { url: "https://example.com/images/sofa1.jpg", alt_text: "브라운 소파 정면" },
  })
  primary_image: { url: string; alt_text: string };

  @ApiProperty({ description: "브랜드", example: { id: 2, name: "편안가구" } })
  brand: { id: number; name: string };

  @ApiProperty({ description: "판매자", example: { id: 1, name: "홈퍼니처" } })
  seller: { id: number; name: string };

  @ApiProperty({ description: "상태", example: "ACTIVE" })
  @IsIn(["ACTIVE", "OUT_OF_STOCK", "DELETED"], {
    message: "status는 ACTIVE, OUT_OF_STOCK 또는 DELETED만 허용됩니다.",
  })
  status: string;

  @ApiProperty({ description: "재고 유무", example: true })
  @IsBoolean()
  in_stock: boolean;

  @ApiProperty({ description: "평점", example: 4.7 })
  @IsNumber()
  @Min(1)
  @Max(5)
  rating: number;

  @ApiProperty({ description: "리뷰 수", example: 128 })
  @IsInt()
  @Min(0)
  review_count: number;

  @ApiProperty({ description: "생성 일시", example: "2025-04-10T09:30:00Z" })
  @IsDate()
  created_at: Date;
}
