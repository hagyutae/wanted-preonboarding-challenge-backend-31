import { Controller, Get, Param, Query } from '@nestjs/common';
import { CategoriesService } from './categories.service';
import { GetCategoryDto } from './dto/get-category.dto';
import { ApiOperation } from '@nestjs/swagger';
import { GetCategoryProductDto } from './dto/get-category-product.dto';

@Controller('categories')
export class CategoriesController {
  constructor(private readonly categoriesService: CategoriesService) {}

  @Get()
  @ApiOperation({ summary: '카테고리 목록 조회' })
  findAll(@Query() getCategoryDto: GetCategoryDto) {
    return this.categoriesService.findAll(getCategoryDto);
  }

  @Get(':id/products')
  @ApiOperation({ summary: '특정 카테고리 조회' })
  findProducts(
    @Param('id') id: string,
    @Query() getCategoryProductDto: GetCategoryProductDto,
  ) {
    return this.categoriesService.findProducts(id, getCategoryProductDto);
  }
}
