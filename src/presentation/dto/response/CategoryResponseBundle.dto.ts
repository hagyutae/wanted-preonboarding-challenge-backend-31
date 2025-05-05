import { ApiProperty, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, ValidateNested } from "class-validator";

import CategoryDTO from "../model/Category.dto";
import ProductSummaryDTO from "../response/ProductSummary.dto";
import PaginationSummaryDTO from "./PaginationSummary.dto";

class CategoryResponseDTO extends PickType(CategoryDTO, [
  "id",
  "name",
  "slug",
  "description",
  "level",
  "image_url",
  "parent",
] as const) {}

export default class CategoryResponseBundleDTO {
  @ApiProperty({ description: "카테고리 정보", type: CategoryResponseDTO })
  @ValidateNested()
  @Type(() => CategoryResponseDTO)
  category: CategoryResponseDTO;

  @ApiProperty({ description: "상품 요약 목록", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ProductSummaryDTO)
  items: ProductSummaryDTO[];

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @ValidateNested()
  @Type(() => PaginationSummaryDTO)
  pagination: PaginationSummaryDTO;
}
