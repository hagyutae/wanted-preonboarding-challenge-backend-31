import { ApiProperty, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import {
  IsArray,
  IsBoolean,
  IsDefined,
  IsIn,
  IsInt,
  IsString,
  ValidateNested,
} from "class-validator";

import ImageDTO from "../model/Image.dto";
import ProductDetailDTO from "../model/ProductDetail.dto";
import ProductOptionGroupDTO from "../model/ProductOptionGroup.dto";
import ProductPriceDTO from "../model/ProductPrice.dto";

class CategoryOfProductBodyDTO {
  @ApiProperty({ description: "카테고리 ID", example: 5 })
  @IsInt()
  category_id: number;

  @ApiProperty({ description: "대표 카테고리 여부", example: true })
  @IsBoolean()
  is_primary: boolean;
}

type TagId = number;

class PriceForProductBodyDTO extends PickType(ProductPriceDTO, [
  "base_price",
  "sale_price",
  "cost_price",
  "currency",
  "tax_rate",
] as const) {}

export default class ProductBodyDTO {
  @ApiProperty({ description: "상품 이름", example: "슈퍼 편안한 소파" })
  @IsString()
  name: string;

  @ApiProperty({ description: "슬러그", example: "super-comfortable-sofa" })
  @IsString()
  slug: string;

  @ApiProperty({ description: "짧은 설명", example: "최고급 소재로 만든 편안한 소파" })
  @IsString()
  short_description: string;

  @ApiProperty({
    description: "상세 설명",
    example: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
  })
  @IsString()
  full_description: string;

  @ApiProperty({ description: "판매자 ID", example: 1 })
  @IsInt()
  seller_id: number;

  @ApiProperty({ description: "브랜드 ID", example: 2 })
  @IsInt()
  brand_id: number;

  @ApiProperty({ description: "상태", example: "ACTIVE" })
  @IsIn(["ACTIVE", "OUT_OF_STOCK", "DELETED"], {
    message: "status는 ACTIVE, OUT_OF_STOCK 또는 DELETED만 허용됩니다.",
  })
  status: string;

  @ApiProperty({ description: "상세 정보", type: ProductDetailDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductDetailDTO)
  detail: ProductDetailDTO;

  @ApiProperty({ description: "가격 정보", type: PriceForProductBodyDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => PriceForProductBodyDTO)
  price: PriceForProductBodyDTO;

  @ApiProperty({ description: "카테고리 목록", type: [CategoryOfProductBodyDTO] })
  @ValidateNested({ each: true })
  @IsArray()
  @Type(() => CategoryOfProductBodyDTO)
  categories: CategoryOfProductBodyDTO[];

  @ApiProperty({ description: "옵션 그룹 목록", type: [ProductOptionGroupDTO] })
  @ValidateNested({ each: true })
  @IsArray()
  @Type(() => ProductOptionGroupDTO)
  option_groups: ProductOptionGroupDTO[];

  @ApiProperty({ description: "이미지 목록", type: [ImageDTO] })
  @ValidateNested({ each: true })
  @IsArray()
  @Type(() => ImageDTO)
  images: ImageDTO[];

  @ApiProperty({ description: "태그 목록", example: [1, 4, 7] })
  @IsArray()
  @IsInt({ each: true })
  tags: TagId[];
}
