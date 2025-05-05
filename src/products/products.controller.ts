import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Put,
  Query,
} from '@nestjs/common';

import { PaginationQueryDto } from './dto/pagination-query.dto';
import { ProductsService } from './products.service';
import { ApiOperation, ApiParam, ApiResponse, ApiBody } from '@nestjs/swagger';
import { UpdateProductDto } from './dto/update-product.dto';
import { CreateProductDto } from './dto/create-product.dto';
import { GetProductReviewDto } from './dto/get-product-review.dto';
import { CreateReviewDto } from './dto/create-review.dto';
import { UpdateReviewDto } from './dto/update-review.dto';

@Controller('products')
export class ProductsController {
  constructor(private readonly productsService: ProductsService) {}

  @Post()
  @ApiOperation({
    summary: '상품 생성',
    description: '새로운 상품을 생성합니다.',
  })
  @ApiBody({ type: CreateProductDto })
  @ApiResponse({ status: 201, description: '상품 생성 성공' })
  create(@Body() createProductDto: CreateProductDto) {
    return this.productsService.create(createProductDto);
  }

  @Get()
  @ApiOperation({
    summary: '상품 목록 조회',
    description: '페이지네이션과 필터링을 이용해 상품 목록을 조회합니다.',
  })
  @ApiResponse({ status: 200, description: '상품 목록 조회 성공' })
  findAll(@Query() paginationQuery: PaginationQueryDto) {
    return this.productsService.findAll(paginationQuery);
  }

  @Get(':id')
  @ApiOperation({
    summary: '특정 상품 조회',
    description: '상품 ID로 상품을 조회합니다.',
  })
  @ApiParam({ name: 'id', type: String, description: '상품 ID' })
  @ApiResponse({ status: 200, description: '상품 조회 성공' })
  @ApiResponse({ status: 404, description: '상품을 찾을 수 없음' })
  findOne(@Param('id') id: string) {
    return this.productsService.findOne(id);
  }

  @Put(':id')
  @ApiOperation({
    summary: '특정 상품 수정',
    description: '상품 ID로 상품을 수정합니다.',
  })
  async update(
    @Param('id') id: string,
    @Body() updateProductDto: UpdateProductDto,
  ) {
    return await this.productsService.update(id, updateProductDto);
  }

  @Delete(':id')
  @ApiOperation({
    summary: '특정 상품 삭제',
    description: '상품 ID로 상품을 삭제합니다.',
  })
  remove(@Param('id') id: string) {
    return this.productsService.remove(id);
  }

  @Get(':id/reviews')
  @ApiOperation({
    summary: '특정 상품 리뷰 조회',
    description: '상품 ID로 상품의 리뷰를 조회합니다.',
  })
  findReviews(
    @Param('id') id: string,
    @Query() paginationQuery: GetProductReviewDto,
  ) {
    return this.productsService.findReviews(id, paginationQuery);
  }

  @Post(':id/reviews')
  @ApiOperation({
    summary: '리뷰 작성',
    description: '상품에 대한 리뷰를 작성합니다.',
  })
  @ApiParam({ name: 'id', type: String, description: '상품 ID' })
  @ApiBody({ type: CreateReviewDto })
  @ApiResponse({ status: 201, description: '리뷰 작성 성공' })
  async createReview(@Param('id') id: string, @Body() dto: CreateReviewDto) {
    return await this.productsService.createReview(id, dto);
  }

  @Put(':id/reviews/:reviewId')
  @ApiOperation({
    summary: '리뷰 수정',
    description: '상품 리뷰를 수정합니다.',
  })
  @ApiParam({ name: 'id', type: String, description: '상품 ID' })
  @ApiParam({ name: 'reviewId', type: String, description: '리뷰 ID' })
  @ApiBody({ type: UpdateReviewDto })
  @ApiResponse({ status: 200, description: '리뷰 수정 성공' })
  async updateReview(
    @Param('id') id: string,
    @Param('reviewId') reviewId: string,
    @Body() dto: UpdateReviewDto,
  ) {
    return await this.productsService.updateReview(id, reviewId, dto);
  }

  @Delete(':id/reviews/:reviewId')
  @ApiOperation({
    summary: '리뷰 삭제',
    description: '상품 리뷰를 삭제합니다.',
  })
  @ApiParam({ name: 'id', type: String, description: '상품 ID' })
  @ApiParam({ name: 'reviewId', type: String, description: '리뷰 ID' })
  @ApiResponse({ status: 200, description: '리뷰 삭제 성공' })
  async deleteReview(
    @Param('id') id: string,
    @Param('reviewId') reviewId: string,
  ) {
    return await this.productsService.deleteReview(id, reviewId);
  }
}
