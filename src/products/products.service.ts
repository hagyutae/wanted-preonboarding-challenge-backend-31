import { Injectable, NotFoundException } from '@nestjs/common';
import { PaginationQueryDto } from './dto/pagination-query.dto';
import { Prisma } from '@prisma/client';
import { PrismaService } from 'src/prisma/prisma.service';
import { UpdateProductDto } from './dto/update-product.dto';
import { CreateProductDto } from './dto/create-product.dto';
import { GetProductReviewDto } from './dto/get-product-review.dto';
import { CreateReviewDto } from './dto/create-review.dto';
import { UpdateReviewDto } from './dto/update-review.dto';

@Injectable()
export class ProductsService {
  constructor(private readonly prisma: PrismaService) {}

  create(createProductDto: CreateProductDto) {
    return this.prisma.products.create({
      data: {
        name: createProductDto.name,
        slug: createProductDto.slug,
        short_description: createProductDto.shortDescription,
        full_description: createProductDto.fullDescription,
        seller_id: BigInt(createProductDto.sellerId),
        brand_id: BigInt(createProductDto.brandId),
        status: createProductDto.status,
        categories: {
          connect: createProductDto.categories.map((category) => ({
            id: BigInt(category.category_id),
          })),
        },
      },
    });
  }

  async findAll(paginationQuery: PaginationQueryDto) {
    const {
      page,
      perPage,
      sort,
      status,
      minPrice,
      maxPrice,
      category,
      seller,
      brand,
      inStock,
      search,
    } = paginationQuery;

    const where: Prisma.productsWhereInput = {};

    if (status) {
      where.status = status;
    }

    if (minPrice || maxPrice) {
      where.prices = {
        some: {
          AND: [
            minPrice ? { base_price: { gte: minPrice } } : {},
            maxPrice ? { base_price: { lte: maxPrice } } : {},
          ],
        },
      };
    }

    if (category && category.length > 0) {
      where.categories = {
        some: {
          category_id: {
            in: category,
          },
        },
      };
    }

    if (seller) {
      where.seller_id = BigInt(seller);
    }

    if (brand) {
      where.brand_id = BigInt(brand);
    }

    if (inStock) {
      where.option_groups = {
        some: {
          options: {
            some: {
              stock: {
                gt: 0,
              },
            },
          },
        },
      };
    }

    if (search) {
      where.OR = [
        { name: { contains: search } },
        { short_description: { contains: search } },
        { full_description: { contains: search } },
      ];
    }

    const orderBy: Prisma.productsOrderByWithRelationInput[] = [];

    if (sort) {
      const sortArray = sort.split(',');
      sortArray.forEach((sortItem) => {
        const [field, direction] = sortItem.split(':');
        orderBy.push({ [field]: direction === 'desc' ? 'desc' : 'asc' });
      });
    }

    const paginationOptions: Prisma.productsFindManyArgs = {
      where,
      orderBy,
      include: {
        prices: true,
        categories: {
          include: {
            category: true,
          },
        },
        seller: true,
        brand: true,
        option_groups: {
          include: {
            options: true,
          },
        },
      },
    };

    if (page && perPage) {
      const skip = (page - 1) * perPage;
      paginationOptions.skip = skip;
      paginationOptions.take = perPage;
    }

    const [total, products] = await Promise.all([
      this.prisma.products.count({ where }),
      this.prisma.products.findMany(paginationOptions),
    ]);

    if (page && perPage) {
      return {
        total,
        page,
        perPage,
        data: products,
      };
    }

    return {
      total,
      data: products,
    };
  }

  async findOne(id: string) {
    const product = await this.prisma.products.findUnique({
      where: {
        id: BigInt(id),
      },
      include: {
        seller: true,
        brand: true,
        details: true,
        prices: true,
        categories: {
          include: {
            category: true,
          },
        },
        option_groups: {
          include: {
            options: true,
          },
        },
      },
    });

    if (!product) {
      throw new NotFoundException('🔴 존재하지 않는 상품입니다.');
    }

    return product;
  }

  async update(id: string, updateProductDto: UpdateProductDto) {
    const product = await this.prisma.products.findUnique({
      where: { id: BigInt(id) },
    });

    if (!product) {
      throw new NotFoundException('🔴 존재하지 않는 상품입니다.');
    }

    const updatedProduct = await this.prisma.products.update({
      where: { id: BigInt(id) },
      data: {
        name: updateProductDto.name,
        slug: updateProductDto.slug,
        short_description: updateProductDto.shortDescription,
        full_description: updateProductDto.fullDescription,
        seller_id: updateProductDto.sellerId,
        brand_id: updateProductDto.brandId,
        status: updateProductDto.status,
        categories: {
          connect: updateProductDto.categories.map((category) => ({
            id: category.category_id,
          })),
        },
        updated_at: new Date(),
      },
    });

    return {
      id: updatedProduct.id,
      name: updatedProduct.name,
      slug: updatedProduct.slug,
      updated_at: updatedProduct.updated_at,
    };
  }

  async remove(id: string) {
    const product = await this.prisma.products.findUnique({
      where: { id: BigInt(id) },
    });

    if (!product) {
      throw new NotFoundException('🔴 존재하지 않는 상품입니다.');
    }

    await this.prisma.products.delete({
      where: { id: BigInt(id) },
    });

    return {
      message: '상품이 삭제되었습니다.',
    };
  }

  async findReviews(id: string, paginationQuery: GetProductReviewDto) {
    const {
      page = 1,
      perPage = 10,
      sort = 'created_at:desc',
      rating,
    } = paginationQuery;
    const productId = BigInt(id);

    // where 조건
    const where: any = { product_id: productId };
    if (rating) {
      where.rating = rating;
    }

    // 전체 개수(필터 적용)
    const totalItems = await this.prisma.reviews.count({ where });

    // 리뷰 목록 (유저 정보 포함)
    const reviews = await this.prisma.reviews.findMany({
      where,
      orderBy: {
        [sort.split(':')[0]]: sort.split(':')[1],
      },
      skip: (page - 1) * perPage,
      take: perPage,
      include: {
        user: true,
      },
    });

    // 평점 분포 및 평균
    const allCounts = await this.prisma.reviews.groupBy({
      by: ['rating'],
      where: { product_id: productId },
      _count: { rating: true },
    });

    const totalCount = await this.prisma.reviews.count({
      where: { product_id: productId },
    });

    const avgObj = await this.prisma.reviews.aggregate({
      where: { product_id: productId },
      _avg: { rating: true },
    });

    // 분포 객체 만들기
    const distribution: Record<string, number> = {
      '5': 0,
      '4': 0,
      '3': 0,
      '2': 0,
      '1': 0,
    };
    allCounts.forEach((row) => {
      distribution[String(row.rating)] = row._count.rating;
    });
    console.log('distribution', distribution);
    // 내림차순 정렬된 분포 객체 생성
    const sortedDistribution = Object.keys(distribution)
      .sort((a, b) => Number(b) - Number(a))
      .reduce(
        (acc, key) => {
          acc[key] = distribution[key];
          return acc;
        },
        {} as Record<string, number>,
      );

    return {
      success: true,
      data: {
        items: reviews.map((r) => ({
          id: Number(r.id),
          user: r.user
            ? {
                id: Number(r.user.id),
                name: r.user.name,
                avatar_url: r.user.avatar_url,
              }
            : null,
          rating: r.rating,
          title: r.title,
          content: r.content,
          created_at: r.created_at,
          updated_at: r.updated_at,
          verified_purchase: r.verified_purchase,
          helpful_votes: r.helpful_votes,
        })),
        summary: {
          average_rating: avgObj._avg.rating
            ? Number(avgObj._avg.rating.toFixed(2))
            : null,
          total_count: totalCount,
          distribution: sortedDistribution,
        },
        pagination: {
          total_items: totalItems,
          total_pages: Math.ceil(totalItems / perPage),
          current_page: page,
          per_page: perPage,
        },
      },
      message: '상품 리뷰를 성공적으로 조회했습니다.',
    };
  }

  async createReview(productId: string, dto: CreateReviewDto) {
    const review = await this.prisma.reviews.create({
      data: {
        product_id: BigInt(productId),
        user_id: BigInt(dto.userId),
        rating: dto.rating,
        title: dto.title,
        content: dto.content,
        verified_purchase: dto.verified_purchase,
        created_at: new Date(),
        updated_at: new Date(),
        helpful_votes: 0,
      },
      include: { user: true },
    });
    return {
      id: Number(review.id),
      ...review,
      user: review.user
        ? {
            id: Number(review.user.id),
            name: review.user.name,
            avatar_url: review.user.avatar_url,
          }
        : null,
    };
  }

  async updateReview(
    productId: string,
    reviewId: string,
    dto: UpdateReviewDto,
  ) {
    const review = await this.prisma.reviews.findUnique({
      where: { id: BigInt(reviewId) },
    });
    if (!review || String(review.product_id) !== productId) {
      throw new NotFoundException('리뷰를 찾을 수 없습니다.');
    }
    const updated = await this.prisma.reviews.update({
      where: { id: BigInt(reviewId) },
      data: {
        rating: dto.rating,
        title: dto.title,
        content: dto.content,
        verified_purchase: dto.verified_purchase,
        user_id: dto.userId,
        updated_at: new Date(),
      },
      include: { user: true },
    });
    return {
      success: true,
      data: {
        id: Number(updated.id),
        ...updated,
        user: updated.user
          ? {
              id: Number(updated.user.id),
              name: updated.user.name,
              avatar_url: updated.user.avatar_url,
            }
          : null,
      },
      message: '리뷰가 수정되었습니다.',
    };
  }

  async deleteReview(productId: string, reviewId: string) {
    const product = await this.prisma.products.findUnique({
      where: { id: BigInt(productId) },
    });

    if (!product) {
      throw new NotFoundException('상품을 찾을 수 없습니다.');
    }
    const review = await this.prisma.reviews.findUnique({
      where: { id: BigInt(reviewId) },
    });

    if (!review || String(review.product_id) !== productId) {
      throw new NotFoundException('리뷰를 찾을 수 없습니다.');
    }

    await this.prisma.reviews.delete({
      where: { id: BigInt(reviewId) },
    });
    return {
      success: true,
      message: '리뷰가 삭제되었습니다.',
    };
  }
}
