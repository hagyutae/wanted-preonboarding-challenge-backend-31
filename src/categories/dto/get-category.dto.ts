import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import { IsNumber } from 'class-validator';

export class GetCategoryDto {
  @ApiProperty({ example: 1, description: '카테고리 레벨' })
  @IsNumber()
  @Type(() => Number)
  level: number;
}
