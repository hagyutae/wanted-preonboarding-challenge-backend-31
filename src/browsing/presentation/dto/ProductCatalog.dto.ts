import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsDate, IsDefined, IsIn, IsInt, IsString, ValidateNested } from "class-validator";

import { ProductOptionGroupDTO } from "@product/application/dto";
import BrandDTO from "@product/presentation/dto/model/Brand.dto";
import ImageDTO from "@product/presentation/dto/model/Image.dto";
import ProductDetailDTO from "@product/presentation/dto/model/ProductDetail.dto";
import ProductPriceDTO from "@product/presentation/dto/model/ProductPrice.dto";
import SellerDTO from "@product/presentation/dto/model/Seller.dto";
import TagDTO from "@product/presentation/dto/model/Tag.dto";
import RatingDTO from "@product/presentation/dto/response/Rating.dto";
import CategoryDTO from "@category/presentation/dto/Category.dto";

export default class ProductCatalogDTO {
  @ApiProperty({ description: "상품 ID", example: 123 })
  @IsInt()
  id: number;

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

  @ApiProperty({ description: "상태", example: "ACTIVE" })
  @IsIn(["ACTIVE", "OUT_OF_STOCK", "DELETED"], {
    message: "status는 ACTIVE, OUT_OF_STOCK 또는 DELETED만 허용됩니다.",
  })
  status: string;

  @ApiProperty({ description: "생성 일시", example: "2025-04-10T09:30:00Z" })
  @IsDate()
  created_at?: Date;

  @ApiProperty({ description: "수정 일시", example: "2025-04-14T10:15:00Z" })
  @IsDate()
  updated_at?: Date;

  @ApiProperty({ description: "판매자 정보", type: SellerDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => SellerDTO)
  seller: SellerDTO;

  @ApiProperty({ description: "브랜드 정보", type: BrandDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => BrandDTO)
  brand: BrandDTO;

  @ApiProperty({ description: "상세 정보", type: ProductDetailDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductDetailDTO)
  detail: ProductDetailDTO;

  @ApiProperty({ description: "가격 정보", type: ProductPriceDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => ProductPriceDTO)
  price: ProductPriceDTO;

  @ApiProperty({ description: "카테고리 목록", type: [CategoryDTO] })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => CategoryDTO)
  categories: CategoryDTO[];

  @ApiProperty({ description: "상품 옵션 그룹", type: [ProductOptionGroupDTO] })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => ProductOptionGroupDTO)
  option_groups: ProductOptionGroupDTO[];

  @ApiProperty({ description: "상품 이미지", type: [ImageDTO] })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => ImageDTO)
  images: ImageDTO[];

  @ApiProperty({ description: "상품 태그", type: [TagDTO] })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => TagDTO)
  tags: TagDTO[];

  @ApiProperty({ description: "상품 평점", type: RatingDTO })
  @IsDefined()
  @ValidateNested()
  @Type(() => RatingDTO)
  rating: RatingDTO;
}
