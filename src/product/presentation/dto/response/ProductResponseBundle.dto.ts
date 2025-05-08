import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsDefined, ValidateNested } from "class-validator";

import { PaginationSummaryDTO } from "@libs/common/dto";
import ProductSummaryDTO from "./ProductSummary.dto";

export default class ProductResponseBundleDTO {
  @ApiProperty({ description: "상품 정보", type: [ProductSummaryDTO] })
  @IsArray()
  @ValidateNested()
  @Type(() => ProductSummaryDTO)
  items: ProductSummaryDTO[];

  @ApiProperty({ description: "페이지네이션 정보", type: PaginationSummaryDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => PaginationSummaryDTO)
  pagination: PaginationSummaryDTO;
}
