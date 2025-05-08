import { ApiProperty, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import {
  IsBoolean,
  IsDefined,
  IsInt,
  IsNumber,
  Matches,
  Max,
  Min,
  ValidateNested,
} from "class-validator";

import BrandDTO from "@product/presentation/dto/model/Brand.dto";
import ImageDTO from "@product/presentation/dto/model/Image.dto";
import SellerDTO from "@product/presentation/dto/model/Seller.dto";
import ProductCatalogDTO from "./ProductCatalog.dto";

class ProductSummaryOfBrandDTO extends PickType(BrandDTO, ["id", "name"] as const) {}
class ProductSummaryOfSellerDTO extends PickType(SellerDTO, ["id", "name"] as const) {}
class ProductSummaryOfImageDTO extends PickType(ImageDTO, ["url", "alt_text"] as const) {}

export default class ProductSummaryDTO extends PickType(ProductCatalogDTO, [
  "id",
  "name",
  "slug",
  "short_description",
  "status",
  "created_at",
] as const) {
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

  @ApiProperty({ description: "주 이미지", type: ProductSummaryOfImageDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductSummaryOfImageDTO)
  primary_image: ProductSummaryOfImageDTO;

  @ApiProperty({ description: "브랜드", type: ProductSummaryOfBrandDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductSummaryOfBrandDTO)
  brand: ProductSummaryOfBrandDTO;

  @ApiProperty({ description: "판매자", type: ProductSummaryOfSellerDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductSummaryOfSellerDTO)
  seller: ProductSummaryOfSellerDTO;

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
}
