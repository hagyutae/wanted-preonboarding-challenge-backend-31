import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsBoolean, IsIn, IsInt, ValidateNested } from "class-validator";

import ImageDTO from "./Image.dto";
import ProductDetailDTO from "./ProductDetail.dto";
import ProductOptionGroupDTO from "./ProductOptionGroup.dto";
import ProductPriceDTO from "./ProductPrice.dto";

class Category {
  @ApiProperty({ description: "카테고리 ID", example: 5 })
  @IsInt()
  category_id: number;

  @ApiProperty({ description: "주요 카테고리 여부", example: true })
  @IsBoolean()
  is_primary: boolean;
}

type TagId = number;

export default class ProductBodyDTO {
  @ApiProperty({ description: "상품 이름", example: "슈퍼 편안한 소파" })
  name: string;

  @ApiProperty({ description: "슬러그", example: "super-comfortable-sofa" })
  slug: string;

  @ApiProperty({ description: "짧은 설명", example: "최고급 소재로 만든 편안한 소파" })
  short_description: string;

  @ApiProperty({
    description: "상세 설명",
    example: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
  })
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
  @ValidateNested()
  @Type(() => ProductDetailDTO)
  detail: ProductDetailDTO;

  @ApiProperty({ description: "가격 정보", type: ProductPriceDTO })
  @ValidateNested()
  @Type(() => ProductPriceDTO)
  price: ProductPriceDTO;

  @ApiProperty({ description: "카테고리 목록", type: [Category] })
  @ValidateNested({ each: true })
  @IsArray()
  @Type(() => Category)
  categories: Category[];

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
