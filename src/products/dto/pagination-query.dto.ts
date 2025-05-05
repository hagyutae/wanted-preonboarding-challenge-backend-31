import {
  IsOptional,
  IsInt,
  Min,
  IsString,
  IsBoolean,
  IsArray,
  Matches,
  Max,
  ValidateIf,
  ValidateBy,
} from 'class-validator';
import { Transform, Type } from 'class-transformer';
import { ApiPropertyOptional } from '@nestjs/swagger';

export class PaginationQueryDto {
  @ApiPropertyOptional({ example: 1, description: '페이지 번호(1부터 시작)' })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  page?: number = 1;

  @ApiPropertyOptional({ example: 10, description: '페이지 당 항목 수' })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  @Max(100)
  perPage?: number = 10;

  @ApiPropertyOptional({
    example: 'created_at:desc',
    description: '정렬 기준(예: created_at:desc,price:asc)',
  })
  @IsOptional()
  @IsString()
  @Matches(/^[a-zA-Z_]+:(asc|desc)(,[a-zA-Z_]+:(asc|desc))*$/, {
    message: '정렬 형식이 올바르지 않습니다. 예: created_at:desc,price:asc',
  })
  sort?: string = 'created_at:desc';

  @ApiPropertyOptional({
    example: 'ACTIVE',
    description: '상품 상태 (ACTIVE, OUT_OF_STOCK, DELETED)',
  })
  @IsOptional()
  @IsString()
  status?: 'ACTIVE' | 'OUT_OF_STOCK' | 'DELETED';

  @ApiPropertyOptional({ example: 1000, description: '최소 가격' })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(0)
  minPrice?: number;

  @ApiPropertyOptional({ example: 5000, description: '최대 가격' })
  @ValidateIf((o) => o.minPrice !== undefined && o.maxPrice !== undefined)
  @ValidateBy({
    name: '최소 금액이 최대 금액보다 큰지 확인',
    validator: {
      validate: (value, args) => {
        const obj = args.object as PaginationQueryDto;
        return obj.maxPrice > obj.minPrice;
      },
      defaultMessage: () => '최대 가격은 최소 가격보다 커야 합니다.',
    },
  })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(0)
  maxPrice?: number;

  @ApiPropertyOptional({
    example: '3,4',
    description: '카테고리 id 목록(콤마로 구분, 예: 1,2)',
    type: String,
  })
  @IsOptional()
  @Transform(({ value }) => {
    if (typeof value === 'string' && value.length > 0) {
      return value.split(',').map((id) => parseInt(id, 10));
    }
    if (Array.isArray(value)) {
      return value.map((id) => parseInt(id, 10));
    }
    return undefined;
  })
  @IsArray()
  @IsInt({ each: true })
  category?: number[];

  @ApiPropertyOptional({ example: 1, description: '판매자 id' })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  seller?: number;

  @ApiPropertyOptional({ example: 1, description: '브랜드 id' })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  brand?: number;

  @ApiPropertyOptional({ example: true, description: '재고 여부' })
  @IsOptional()
  @Type(() => Boolean)
  @IsBoolean()
  inStock?: boolean;

  @ApiPropertyOptional({ example: '노트북', description: '검색어' })
  @IsOptional()
  @IsString()
  search?: string;
}
