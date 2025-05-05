import { ApiPropertyOptional } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import { IsOptional, IsInt, Min, IsString, IsBoolean } from 'class-validator';

export class GetCategoryProductDto {
  @ApiPropertyOptional({ example: 1, description: '페이지 번호', default: 1 })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  page?: number = 1;

  @ApiPropertyOptional({
    example: 10,
    description: '페이지당 아이템 수',
    default: 10,
  })
  @IsOptional()
  @Type(() => Number)
  @IsInt()
  @Min(1)
  perPage?: number = 10;

  @ApiPropertyOptional({
    example: 'created_at:desc',
    description: '정렬 기준. 형식: {필드}:{asc|desc}',
    default: 'created_at:desc',
  })
  @IsOptional()
  @IsString()
  sort?: string = 'created_at:desc';

  @ApiPropertyOptional({
    example: true,
    description: '하위 카테고리 포함 여부',
    default: true,
  })
  @IsOptional()
  @Type(() => Boolean)
  @IsBoolean()
  includeSubcategories?: boolean = true;
}
