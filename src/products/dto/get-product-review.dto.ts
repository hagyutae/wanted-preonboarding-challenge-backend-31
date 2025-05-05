import { PickType } from '@nestjs/swagger';
import { PaginationQueryDto } from './pagination-query.dto';
import { IsNumber, IsOptional, Max, Min } from 'class-validator';

export class GetProductReviewDto extends PickType(PaginationQueryDto, [
  'page',
  'perPage',
  'sort',
]) {
  @IsNumber()
  @Min(1)
  @Max(5)
  @IsOptional()
  rating?: number;
}
