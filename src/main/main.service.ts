import { Injectable } from '@nestjs/common';
import { PrismaService } from 'src/prisma/prisma.service';

@Injectable()
export class MainService {
  constructor(private readonly prisma: PrismaService) {}

  async getMain() {
    // 1. 신규 상품 (created_at 기준 최신 10개)
    const newProducts = await this.prisma.products.findMany({
      orderBy: { created_at: 'desc' },
      take: 10,
      include: {
        prices: { orderBy: { id: 'asc' }, take: 1 },
        images: { where: { is_primary: true }, take: 1 },
        brand: true,
        seller: true,
        reviews: true,
        option_groups: { include: { options: true } },
      },
    });

    // 2. 인기 상품 (리뷰 수 기준 상위 10개)
    const popularProducts = await this.prisma.products.findMany({
      orderBy: [{ reviews: { _count: 'desc' } }, { created_at: 'desc' }],
      take: 10,
      include: {
        prices: { orderBy: { id: 'asc' }, take: 1 },
        images: { where: { is_primary: true }, take: 1 },
        brand: true,
        seller: true,
        reviews: true,
        option_groups: { include: { options: true } },
      },
    });

    const categories = await this.prisma.categories.findMany({
      where: { level: 1 },
      include: {
        _count: {
          select: { products: true },
        },
      },
      orderBy: {
        products: {
          _count: 'desc',
        },
      },
      take: 10,
    });

    // 데이터 가공
    const mapProduct = (p: any) => ({
      id: Number(p.id),
      name: p.name,
      slug: p.slug,
      short_description: p.short_description,
      base_price: p.prices?.[0]?.base_price
        ? Number(p.prices[0].base_price)
        : null,
      sale_price: p.prices?.[0]?.sale_price
        ? Number(p.prices[0].sale_price)
        : null,
      currency: p.prices?.[0]?.currency ?? 'KRW',
      primary_image: p.images?.[0]
        ? {
            url: p.images[0].url,
            alt_text: p.images[0].alt_text,
          }
        : null,
      brand: p.brand
        ? {
            id: Number(p.brand.id),
            name: p.brand.name,
          }
        : null,
      seller: p.seller
        ? {
            id: Number(p.seller.id),
            name: p.seller.name,
          }
        : null,
      rating:
        p.reviews && p.reviews.length > 0
          ? Number(
              (
                p.reviews.reduce((sum, r) => sum + r.rating, 0) /
                p.reviews.length
              ).toFixed(2),
            )
          : null,
      review_count: p.reviews?.length ?? 0,
      in_stock:
        p.option_groups?.some((og) =>
          og.options?.some((opt) => opt.stock > 0),
        ) ?? false,
      status: p.status,
      created_at: p.created_at,
    });

    return {
      success: true,
      data: {
        new_products: newProducts.map(mapProduct),
        popular_products: popularProducts.map(mapProduct),
        featured_categories: categories.map((c) => ({
          id: Number(c.id),
          name: c.name,
          slug: c.slug,
          image_url: c.image_url,
          product_count: c._count.products,
        })),
      },
      message: '메인 페이지 상품 목록을 성공적으로 조회했습니다.',
    };
  }
}
