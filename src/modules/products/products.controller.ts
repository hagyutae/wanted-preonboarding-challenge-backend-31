import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Query,
  Put,
  ParseIntPipe,
} from '@nestjs/common';

import { CreateReviewResponseDto } from '../reviews/dto/review.dto';
import { CreateReviewRequestDto } from '../reviews/dto/review.dto';
import { GetReviewsRequestDto } from '../reviews/dto/review.dto';
import { ProductsService } from './products.service';
import { GetReviewsResponseDto } from '../reviews/dto/review.dto';
import { ReviewsService } from '../reviews/reviews.service';
import {
  createPaginatedData,
  createSuccessResponse,
  createPaginatedResponse,
} from '~/common/utils/response.util';
import {
  CreateProductRequestDto,
  CreateProductResponseDto,
  GetProductResponseDto,
  GetProductsRequestDto,
  GetProductsResponseDto,
  UpdateProductRequestDto,
  UpdateProductResponseDto,
} from './dto/product.dto';
import { DeleteResponseDto } from '~/common/dto/response.dto';
import { RandomUser } from '~/common/decorators/random-user.decorator';
@Controller('products')
export class ProductsController {
  constructor(
    private readonly productsService: ProductsService,
    private readonly reviewsService: ReviewsService,
  ) {}

  @Get()
  async getProducts(
    @Query() query: GetProductsRequestDto,
  ): Promise<GetProductsResponseDto> {
    const { items, total } = await this.productsService.getProducts(query);
    return createSuccessResponse(
      createPaginatedData(items, total, {
        page: query.page,
        per_page: query.per_page,
      }),
      '상품 목록을 성공적으로 조회했습니다.',
    );
  }

  @Get(':id')
  async getProduct(@Param('id') id: number): Promise<GetProductResponseDto> {
    return createSuccessResponse(
      await this.productsService.getProduct(id),
      '상품 상세 정보를 성공적으로 조회했습니다.',
    );
  }

  @Post()
  async createProduct(
    @Body() dto: CreateProductRequestDto,
  ): Promise<CreateProductResponseDto> {
    return createSuccessResponse(
      await this.productsService.createProduct(dto),
      '상품이 성공적으로 등록되었습니다.',
    );
  }

  @Put(':id')
  async updateProduct(
    @Param('id') id: number,
    @Body() dto: UpdateProductRequestDto,
  ): Promise<UpdateProductResponseDto> {
    return createSuccessResponse(
      await this.productsService.updateProduct(id, dto),
      '상품이 성공적으로 수정되었습니다.',
    );
  }

  @Delete(':id')
  async deleteProduct(@Param('id') id: number): Promise<DeleteResponseDto> {
    await this.productsService.deleteProduct(id);
    return createSuccessResponse(null, '상품이 성공적으로 삭제되었습니다.');
  }

  @Get(':id/reviews')
  async getReviews(
    @Param('id', new ParseIntPipe()) productId: number,
    @Query() query: GetReviewsRequestDto,
  ): Promise<GetReviewsResponseDto> {
    const { items, summary } = await this.reviewsService.getReviews(
      productId,
      query,
    );
    const { page, per_page } = query;
    return createSuccessResponse(
      {
        summary,
        ...createPaginatedData(items, summary.totalCount, {
          page,
          per_page,
        }),
      },
      '상품 리뷰를 성공적으로 조회했습니다.',
    );
  }

  @Post(':id/reviews')
  async createReview(
    @Param('id', new ParseIntPipe()) productId: number,
    @Body() dto: CreateReviewRequestDto,
    @RandomUser() userId: number,
  ): Promise<CreateReviewResponseDto> {
    return createSuccessResponse(
      await this.reviewsService.createReview(productId, userId, dto),
      '리뷰가 성공적으로 등록되었습니다.',
    );
  }
}
