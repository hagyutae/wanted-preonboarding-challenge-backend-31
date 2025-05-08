import { ApiProperty, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsInt, Min, ValidateNested } from "class-validator";

import { CategoryDTO } from "@category/presentation/dto";
import ProductSummaryDTO from "./ProductSummary.dto";

export class FeaturedCategoryDTO extends PickType(CategoryDTO, [
  "id",
  "name",
  "slug",
  "image_url",
] as const) {
  @ApiProperty({ description: "제품 수", example: 85 })
  @IsInt()
  @Min(0)
  product_count: number;
}

export default class MainResponseBundleDTO {
  @ApiProperty({ description: "신상품 목록", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ProductSummaryDTO)
  new_products: ProductSummaryDTO[];

  @ApiProperty({ description: "인기 상품 목록", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ProductSummaryDTO)
  popular_products: ProductSummaryDTO[];

  @ApiProperty({ description: "추천 카테고리 목록", type: [FeaturedCategoryDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => FeaturedCategoryDTO)
  featured_categories: FeaturedCategoryDTO[];
}
