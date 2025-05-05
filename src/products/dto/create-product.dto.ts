import {
  IsString,
  IsNumber,
  IsOptional,
  IsArray,
  ValidateNested,
  IsBoolean,
} from 'class-validator';
import { Type } from 'class-transformer';
import { ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';

export class DimensionsDto {
  @ApiProperty({ example: 200 })
  @IsNumber()
  width: number;

  @ApiProperty({ example: 85 })
  @IsNumber()
  height: number;

  @ApiProperty({ example: 90 })
  @IsNumber()
  depth: number;
}

export class AdditionalInfoDto {
  @ApiProperty({ example: true })
  @IsBoolean()
  assembly_required: boolean;

  @ApiProperty({ example: '30분' })
  @IsString()
  assembly_time: string;
}

export class DetailDto {
  @ApiProperty({ example: 25.5 })
  @IsNumber()
  weight: number;

  @ApiProperty({
    type: DimensionsDto,
    example: { width: 200, height: 85, depth: 90 },
  })
  @ValidateNested()
  @Type(() => DimensionsDto)
  dimensions: DimensionsDto;

  @ApiProperty({ example: '가죽, 목재, 폼' })
  @IsString()
  materials: string;

  @ApiProperty({ example: '대한민국' })
  @IsString()
  country_of_origin: string;

  @ApiProperty({ example: '2년 품질 보증' })
  @IsString()
  warranty_info: string;

  @ApiProperty({ example: '마른 천으로 표면을 닦아주세요' })
  @IsString()
  care_instructions: string;

  @ApiProperty({
    type: AdditionalInfoDto,
    example: { assembly_required: true, assembly_time: '30분' },
  })
  @ValidateNested()
  @Type(() => AdditionalInfoDto)
  additional_info: AdditionalInfoDto;
}

export class PriceDto {
  @ApiProperty({ example: 599000 })
  @IsNumber()
  base_price: number;

  @ApiProperty({ example: 499000 })
  @IsNumber()
  sale_price: number;

  @ApiProperty({ example: 350000 })
  @IsNumber()
  cost_price: number;

  @ApiProperty({ example: 'KRW' })
  @IsString()
  currency: string;

  @ApiProperty({ example: 10 })
  @IsNumber()
  tax_rate: number;
}

export class CategoryDto {
  @ApiProperty({ example: 5 })
  @IsNumber()
  category_id: number;

  @ApiProperty({ example: true })
  @IsBoolean()
  is_primary: boolean;
}

export class OptionDto {
  @ApiProperty({ example: '브라운' })
  @IsString()
  name: string;

  @ApiProperty({ example: 0 })
  @IsNumber()
  additional_price: number;

  @ApiProperty({ example: 'SOFA-BRN' })
  @IsString()
  sku: string;

  @ApiProperty({ example: 10 })
  @IsNumber()
  stock: number;

  @ApiProperty({ example: 1 })
  @IsNumber()
  display_order: number;
}

export class OptionGroupDto {
  @ApiProperty({ example: '색상' })
  @IsString()
  name: string;

  @ApiProperty({ example: 1 })
  @IsNumber()
  display_order: number;

  @ApiProperty({
    type: [OptionDto],
    example: [
      {
        name: '브라운',
        additional_price: 0,
        sku: 'SOFA-BRN',
        stock: 10,
        display_order: 1,
      },
      {
        name: '블랙',
        additional_price: 0,
        sku: 'SOFA-BLK',
        stock: 15,
        display_order: 2,
      },
    ],
  })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => OptionDto)
  options: OptionDto[];
}

export class ImageDto {
  @ApiProperty({ example: 'https://example.com/images/sofa1.jpg' })
  @IsString()
  url: string;

  @ApiProperty({ example: '브라운 소파 정면' })
  @IsString()
  alt_text: string;

  @ApiProperty({ example: true })
  @IsBoolean()
  is_primary: boolean;

  @ApiProperty({ example: 1 })
  @IsNumber()
  display_order: number;

  @ApiPropertyOptional({ example: null })
  @IsOptional()
  @IsNumber()
  option_id?: number | null;
}

export class CreateProductDto {
  @ApiProperty({ example: '슈퍼 편안한 소파2' })
  @IsString()
  name: string;

  @ApiProperty({ example: 'super-comfortable-sofa-two' })
  @IsString()
  slug: string;

  @ApiProperty({ example: '최고급 소재로 만든 편안한 소파' })
  @IsString()
  shortDescription: string;

  @ApiProperty({ example: '<p>이 소파는 최고급 소재로 제작되었으며...</p>' })
  @IsString()
  fullDescription: string;

  @ApiProperty({ example: 1 })
  @IsNumber()
  sellerId: number;

  @ApiProperty({ example: 2 })
  @IsNumber()
  brandId: number;

  @ApiProperty({
    enum: ['ACTIVE', 'OUT_OF_STOCK', 'DELETED'],
    example: 'ACTIVE',
  })
  @IsString()
  status: string;

  @ApiProperty({
    type: DetailDto,
    example: {
      weight: 25.5,
      dimensions: { width: 200, height: 85, depth: 90 },
      materials: '가죽, 목재, 폼',
      country_of_origin: '대한민국',
      warranty_info: '2년 품질 보증',
      care_instructions: '마른 천으로 표면을 닦아주세요',
      additional_info: { assembly_required: true, assembly_time: '30분' },
    },
  })
  @ValidateNested()
  @Type(() => DetailDto)
  detail: DetailDto;

  @ApiProperty({
    type: PriceDto,
    example: {
      base_price: 599000,
      sale_price: 499000,
      cost_price: 350000,
      currency: 'KRW',
      tax_rate: 10,
    },
  })
  @ValidateNested()
  @Type(() => PriceDto)
  price: PriceDto;

  @ApiProperty({
    type: [CategoryDto],
    example: [
      { category_id: 5, is_primary: true },
      { category_id: 8, is_primary: false },
    ],
  })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => CategoryDto)
  categories: CategoryDto[];

  @ApiProperty({
    type: [OptionGroupDto],
    example: [
      {
        name: '색상',
        display_order: 1,
        options: [
          {
            name: '브라운',
            additional_price: 0,
            sku: 'SOFA-BRN',
            stock: 10,
            display_order: 1,
          },
          {
            name: '블랙',
            additional_price: 0,
            sku: 'SOFA-BLK',
            stock: 15,
            display_order: 2,
          },
        ],
      },
      {
        name: '소재',
        display_order: 2,
        options: [
          {
            name: '천연 가죽',
            additional_price: 100000,
            sku: 'SOFA-LTHR',
            stock: 5,
            display_order: 1,
          },
          {
            name: '인조 가죽',
            additional_price: 0,
            sku: 'SOFA-FAKE',
            stock: 20,
            display_order: 2,
          },
        ],
      },
    ],
  })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => OptionGroupDto)
  option_groups: OptionGroupDto[];

  @ApiProperty({
    type: [ImageDto],
    example: [
      {
        url: 'https://example.com/images/sofa1.jpg',
        alt_text: '브라운 소파 정면',
        is_primary: true,
        display_order: 1,
        option_id: null,
      },
      {
        url: 'https://example.com/images/sofa2.jpg',
        alt_text: '브라운 소파 측면',
        is_primary: false,
        display_order: 2,
        option_id: null,
      },
    ],
  })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => ImageDto)
  images: ImageDto[];

  @ApiProperty({ type: [Number], example: [1, 4, 7] })
  @IsArray()
  @IsNumber({}, { each: true })
  tags: number[];
}
