import {
  Controller,
  Get,
  Param,
  ParseIntPipe,
  Query,
  UsePipes,
} from '@nestjs/common';

import { CategoriesService } from './categories.service';
import { ZodValidationPipe } from '~/common/pipes/zod-validation.pipe';
import {
  GetCategoriesRequestDto,
  GetCategoriesRequestDtoSchema,
  GetProductsByCategoryIdRequestDto,
  GetProductsByCategoryIdRequestDtoSchema,
} from './dto/category.dto';

@Controller('categories')
export class CategoriesController {
  constructor(private readonly categoriesService: CategoriesService) {}

  @Get()
  @UsePipes(new ZodValidationPipe(GetCategoriesRequestDtoSchema))
  async getCategories(@Query() query: GetCategoriesRequestDto) {
    return this.categoriesService.getCategories(query.level);
  }

  @Get(':id/products')
  @UsePipes(new ZodValidationPipe(GetProductsByCategoryIdRequestDtoSchema))
  async getProductsByCategoryId(
    @Param('id', new ParseIntPipe()) id: number,
    @Query() query: GetProductsByCategoryIdRequestDto,
  ) {
    return this.categoriesService.getProductsByCategoryId(id, query);
  }
}
