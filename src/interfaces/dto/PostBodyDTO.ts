import { ApiProperty } from "@nestjs/swagger";

class Dimensions {
  @ApiProperty({ description: "가로 길이", example: 100 })
  width: number;

  @ApiProperty({ description: "세로 길이", example: 200 })
  height: number;

  @ApiProperty({ description: "깊이", example: 50 })
  depth: number;
}

class AdditionalInfo {
  @ApiProperty({ description: "조립 필요 여부", example: true })
  assembly_required: boolean;

  @ApiProperty({ description: "조립 시간", example: "30분" })
  assembly_time: string;
}

class Detail {
  @ApiProperty({ description: "무게", example: 10 })
  weight: number;

  @ApiProperty({ description: "크기 정보", type: Dimensions })
  dimensions: Dimensions;

  @ApiProperty({ description: "재료", example: "나무" })
  materials: string;

  @ApiProperty({ description: "원산지", example: "대한민국" })
  country_of_origin: string;

  @ApiProperty({ description: "보증 정보", example: "1년" })
  warranty_info: string;

  @ApiProperty({ description: "관리 지침", example: "물로 닦지 마세요" })
  care_instructions: string;

  @ApiProperty({ description: "추가 정보", type: AdditionalInfo })
  additional_info: AdditionalInfo;
}

class Price {
  @ApiProperty({ description: "기본 가격", example: 100000 })
  base_price: number;

  @ApiProperty({ description: "할인 가격", example: 90000 })
  sale_price: number;

  @ApiProperty({ description: "원가", example: 80000 })
  cost_price: number;

  @ApiProperty({ description: "통화", example: "KRW" })
  currency: string;

  @ApiProperty({ description: "세율", example: 10 })
  tax_rate: number;
}

class Category {
  @ApiProperty({ description: "카테고리 ID", example: 1 })
  category_id: number;

  @ApiProperty({ description: "주요 카테고리 여부", example: true })
  is_primary: boolean;
}

class Option {
  @ApiProperty({ description: "옵션 이름", example: "색상" })
  name: string;

  @ApiProperty({ description: "추가 가격", example: 5000 })
  additional_price: number;

  @ApiProperty({ description: "SKU", example: "SKU12345" })
  sku: string;

  @ApiProperty({ description: "재고", example: 100 })
  stock: number;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;
}

class OptionGroup {
  @ApiProperty({ description: "옵션 그룹 이름", example: "색상 선택" })
  name: string;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;

  @ApiProperty({ description: "옵션 목록", type: [Option] })
  options: Option[];
}

class Image {
  @ApiProperty({
    description: "이미지 URL",
    example: "https://example.com/image.jpg",
  })
  url: string;

  @ApiProperty({ description: "대체 텍스트", example: "상품 이미지" })
  alt_text: string;

  @ApiProperty({ description: "주요 이미지 여부", example: true })
  is_primary: boolean;

  @ApiProperty({ description: "표시 순서", example: 1 })
  display_order: number;

  @ApiProperty({ description: "옵션 ID", example: null })
  option_id: number | null;
}

export default class PostBodyDTO {
  @ApiProperty({ description: "상품 이름", example: "의자" })
  name: string;

  @ApiProperty({ description: "슬러그", example: "chair" })
  slug: string;

  @ApiProperty({ description: "짧은 설명", example: "편안한 의자" })
  short_description: string;

  @ApiProperty({
    description: "상세 설명",
    example: "이 의자는 매우 편안합니다.",
  })
  full_description: string;

  @ApiProperty({ description: "판매자 ID", example: 1 })
  seller_id: number;

  @ApiProperty({ description: "브랜드 ID", example: 2 })
  brand_id: number;

  @ApiProperty({ description: "상태", example: "판매중" })
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

  @ApiProperty({ description: "태그 목록", example: [1, 2, 3] })
  tags: number[];
}
