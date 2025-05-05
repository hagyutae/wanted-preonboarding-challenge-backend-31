import { ApiProperty } from "@nestjs/swagger";
import { IsArray, IsDate, IsIn, IsInt } from "class-validator";

import {
  Brand,
  Product_Detail,
  Product_Image,
  Product_Price,
  Seller,
  Tag,
} from "src/domain/entities";
import { ProductCategoryDTO, ProductOptionGroupDTO } from "src/application/dto";

export default class ProductCatalogDTO {
  @ApiProperty({ description: "상품 ID", example: 123 })
  @IsInt()
  public id: number;

  @ApiProperty({ description: "상품 이름", example: "슈퍼 편안한 소파" })
  public name: string;

  @ApiProperty({ description: "슬러그", example: "super-comfortable-sofa" })
  public slug: string;

  @ApiProperty({ description: "짧은 설명", example: "최고급 소재로 만든 편안한 소파" })
  public short_description: string;

  @ApiProperty({
    description: "상세 설명",
    example: "<p>이 소파는 최고급 소재로 제작되었으며...</p>",
  })
  public full_description: string;

  @ApiProperty({ description: "상태", example: "ACTIVE" })
  @IsIn(["ACTIVE", "OUT_OF_STOCK", "DELETED"], {
    message: "status는 ACTIVE, OUT_OF_STOCK 또는 DELETED만 허용됩니다.",
  })
  status: string;

  @ApiProperty({ description: "생성 일시", example: "2025-04-10T09:30:00Z" })
  @IsDate()
  public created_at: Date;

  @ApiProperty({ description: "수정 일시", example: "2025-04-14T10:15:00Z" })
  @IsDate()
  public updated_at: Date;

  @ApiProperty({
    example: {
      id: 1,
      name: "홈퍼니처",
      description: "최고의 가구 전문 판매점",
      logo_url: "https://example.com/sellers/homefurniture.png",
      rating: 4.8,
      contact_email: "contact@homefurniture.com",
      contact_phone: "02-1234-5678",
    },
  })
  public seller: Seller;

  @ApiProperty({
    example: {
      id: 2,
      name: "편안가구",
      description: "편안함에 집중한 프리미엄 가구 브랜드",
      logo_url: "https://example.com/brands/comfortfurniture.png",
      website: "https://comfortfurniture.com",
    },
  })
  public brand: Brand;

  @ApiProperty({
    example: {
      weight: 25.5,
      dimensions: {
        width: 200,
        height: 85,
        depth: 90,
      },
      materials: "가죽, 목재, 폼",
      country_of_origin: "대한민국",
      warranty_info: "2년 품질 보증",
      care_instructions: "마른 천으로 표면을 닦아주세요",
      additional_info: {
        assembly_required: true,
        assembly_time: "30분",
      },
    },
  })
  public detail: Product_Detail;

  @ApiProperty({
    example: {
      base_price: 599000,
      sale_price: 499000,
      currency: "KRW",
      tax_rate: 10,
      discount_percentage: 17,
    },
  })
  public price: Product_Price & { discount_percentage: number };

  @ApiProperty({
    example: [
      {
        id: 5,
        name: "소파",
        slug: "sofa",
        is_primary: true,
        parent: {
          id: 2,
          name: "거실 가구",
          slug: "living-room",
        },
      },
      {
        id: 8,
        name: "3인용 소파",
        slug: "3-seater-sofa",
        is_primary: false,
        parent: {
          id: 5,
          name: "소파",
          slug: "sofa",
        },
      },
    ],
  })
  @IsArray()
  public categories: ProductCategoryDTO[];

  @ApiProperty({
    example: [
      {
        id: 15,
        name: "색상",
        display_order: 1,
        options: [
          {
            id: 31,
            name: "브라운",
            additional_price: 0,
            sku: "SOFA-BRN",
            stock: 10,
            display_order: 1,
          },
          {
            id: 32,
            name: "블랙",
            additional_price: 0,
            sku: "SOFA-BLK",
            stock: 15,
            display_order: 2,
          },
        ],
      },
      {
        id: 16,
        name: "소재",
        display_order: 2,
        options: [
          {
            id: 33,
            name: "천연 가죽",
            additional_price: 100000,
            sku: "SOFA-LTHR",
            stock: 5,
            display_order: 1,
          },
          {
            id: 34,
            name: "인조 가죽",
            additional_price: 0,
            sku: "SOFA-FAKE",
            stock: 20,
            display_order: 2,
          },
        ],
      },
    ],
  })
  @IsArray()
  public option_groups: ProductOptionGroupDTO[];

  @ApiProperty({
    example: [
      {
        id: 150,
        url: "https://example.com/images/sofa1.jpg",
        alt_text: "브라운 소파 정면",
        is_primary: true,
        display_order: 1,
        option_id: null,
      },
      {
        id: 151,
        url: "https://example.com/images/sofa2.jpg",
        alt_text: "브라운 소파 측면",
        is_primary: false,
        display_order: 2,
        option_id: null,
      },
    ],
  })
  @IsArray()
  public images: Product_Image[];

  @ApiProperty({
    example: [
      {
        id: 1,
        name: "편안함",
        slug: "comfort",
      },
      {
        id: 4,
        name: "프리미엄",
        slug: "premium",
      },
      {
        id: 7,
        name: "거실 가구",
        slug: "living-room-furniture",
      },
    ],
  })
  @IsArray()
  public tags: Tag[];

  @ApiProperty({
    example: {
      average: 4.7,
      count: 128,
      distribution: {
        "5": 95,
        "4": 20,
        "3": 10,
        "2": 2,
        "1": 1,
      },
    },
  })
  public rating: {
    average: number;
    count: number;
    distribution: {
      "5": number;
      "4": number;
      "3": number;
      "2": number;
      "1": number;
    };
  };
}
