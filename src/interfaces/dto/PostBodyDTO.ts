import { ApiProperty } from "@nestjs/swagger";

class Dimensions {
  @ApiProperty({ description: "가로 길이", example: 200 })
  width: number;

  @ApiProperty({ description: "세로 길이", example: 85 })
  height: number;

  @ApiProperty({ description: "깊이", example: 90 })
  depth: number;
}

class AdditionalInfo {
  @ApiProperty({ description: "조립 필요 여부", example: true })
  assembly_required: boolean;

  @ApiProperty({ description: "조립 시간", example: "30분" })
  assembly_time: string;
}

class Detail {
  @ApiProperty({ description: "무게", example: 25.5 })
  weight: number;

  @ApiProperty({ description: "크기 정보", type: Dimensions })
  dimensions: Dimensions;

  @ApiProperty({ description: "재료", example: "가죽, 목재, 폼" })
  materials: string;

  @ApiProperty({ description: "원산지", example: "대한민국" })
  country_of_origin: string;

  @ApiProperty({ description: "보증 정보", example: "2년 품질 보증" })
  warranty_info: string;

  @ApiProperty({ description: "관리 지침", example: "마른 천으로 표면을 닦아주세요" })
  care_instructions: string;

  @ApiProperty({ description: "추가 정보", type: AdditionalInfo })
  additional_info: AdditionalInfo;
}

class Price {
  @ApiProperty({ description: "기본 가격", example: 599000 })
  base_price: number;

  @ApiProperty({ description: "할인 가격", example: 499000 })
  sale_price: number;

  @ApiProperty({ description: "원가", example: 350000 })
  cost_price: number;

  @ApiProperty({ description: "통화", example: "KRW" })
  currency: string;

  @ApiProperty({ description: "세율", example: 10 })
  tax_rate: number;
}

class Category {
  @ApiProperty({ description: "카테고리 ID", example: 5 })
  category_id: number;

  @ApiProperty({ description: "주요 카테고리 여부", example: true })
  is_primary: boolean;
}

class Option {
  @ApiProperty({ description: "옵션 이름", example: "브라운" })
  name: string;

  @ApiProperty({ description: "추가 가격", example: 0 })
  additional_price: number;

  @ApiProperty({ description: "SKU", example: "SOFA-BRN" })
  sku: string;

  @ApiProperty({ description: "재고", example: 10 })
  stock: number;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;
}

class OptionGroup {
  @ApiProperty({ description: "옵션 그룹 이름", example: "색상" })
  name: string;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;

  @ApiProperty({ description: "옵션 목록", type: [Option] })
  options: Option[];
}

class Image {
  @ApiProperty({
    description: "이미지 URL",
    example: "https://example.com/images/sofa1.jpg",
  })
  url: string;

  @ApiProperty({ description: "대체 텍스트", example: "브라운 소파 정면" })
  alt_text: string;

  @ApiProperty({ description: "주요 이미지 여부", example: true })
  is_primary: boolean;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;

  @ApiProperty({ description: "옵션 ID", example: null })
  option_id: number | null;
}

export default class PostBodyDTO {
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
  seller_id: number;

  @ApiProperty({ description: "브랜드 ID", example: 2 })
  brand_id: number;

  @ApiProperty({ description: "상태", example: "ACTIVE" })
  status: string;

  @ApiProperty({ description: "상세 정보", type: Detail })
  detail: Detail;

  @ApiProperty({ description: "가격 정보", type: Price })
  price: Price;

  @ApiProperty({ description: "카테고리 목록", type: [Category] })
  categories: Category[];

  @ApiProperty({ description: "옵션 그룹 목록", type: [OptionGroup] })
  option_groups: OptionGroup[];

  @ApiProperty({ description: "이미지 목록", type: [Image] })
  images: Image[];

  @ApiProperty({ description: "태그 목록", example: [1, 4, 7] })
  tags: number[];
}
