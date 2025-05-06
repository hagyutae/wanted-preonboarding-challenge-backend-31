import {
  Controller,
  Get,
  Post,
  Body,
  Patch,
  Param,
  Delete,
  Query,
} from '@nestjs/common';
import { CreateReviewResponseDto } from '../reviews/dto/review.dto';
import { CreateReviewRequestDto } from '../reviews/dto/review.dto';
import { GetReviewsRequestDto } from '../reviews/dto/review.dto';
import { ProductsService } from './products.service';
import { CreateProductDto } from './dto/create-product.dto';
import { UpdateProductDto } from './dto/update-product.dto';
import { GetReviewsResponseDto } from '../reviews/dto/review.dto';
import { ReviewsService } from '../reviews/reviews.service';
import {
  createPaginatedData,
  createSuccessResponse,
} from '~/common/utils/response.util';

@Controller('products')
export class ProductsController {
  constructor(
    private readonly productsService: ProductsService,
    private readonly reviewsService: ReviewsService,
  ) {}

  @Post()
  create(@Body() createProductDto: CreateProductDto) {
    return this.productsService.create(createProductDto);
  }

  @Get()
  findAll() {
    return this.productsService.findAll();
  }

  @Get(':id/reviews')
  async getReviews(
    @Param('id') productId: number,
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
    @Param('id') productId: number,
    @Body() dto: CreateReviewRequestDto,
  ): Promise<CreateReviewResponseDto> {
    return createSuccessResponse(
      await this.reviewsService.createReview(productId, dto),
      '리뷰가 성공적으로 등록되었습니다.',
    );
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.productsService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateProductDto: UpdateProductDto) {
    return this.productsService.update(+id, updateProductDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.productsService.remove(+id);
  }
}
