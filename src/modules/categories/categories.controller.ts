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
  GetProductsByCategoryIdResponseDto,
  GetCategoriesResponseDto,
} from './dto/category.dto';
import {
  createPaginatedData,
  createSuccessResponse,
} from '~/common/utils/response.util';

@Controller('categories')
export class CategoriesController {
  constructor(private readonly categoriesService: CategoriesService) {}

  @Get()
  @UsePipes(new ZodValidationPipe(GetCategoriesRequestDtoSchema))
  async getCategories(
    @Query() query: GetCategoriesRequestDto,
  ): Promise<GetCategoriesResponseDto> {
    return createSuccessResponse(
      await this.categoriesService.getCategories(query.level),
      '카테고리 목록을 성공적으로 조회했습니다.',
    );
  }

  @Get(':id/products')
  @UsePipes(new ZodValidationPipe(GetProductsByCategoryIdRequestDtoSchema))
  async getProductsByCategoryId(
    @Param('id', new ParseIntPipe()) id: number,
    @Query() query: GetProductsByCategoryIdRequestDto,
  ): Promise<GetProductsByCategoryIdResponseDto> {
    const { items, total } =
      await this.categoriesService.getProductsByCategoryId(id, query);
    return createSuccessResponse(
      {
        category: await this.categoriesService.getCategoryById(id),
        ...createPaginatedData(items, total, query),
      },
      '카테고리 상품 목록을 성공적으로 조회했습니다.',
    );
  }
}
