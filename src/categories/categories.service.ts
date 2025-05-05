import { BadRequestException, Injectable } from '@nestjs/common';
import { PrismaService } from 'src/prisma/prisma.service';
import { GetCategoryDto } from './dto/get-category.dto';
import { GetCategoryProductDto } from './dto/get-category-product.dto';

@Injectable()
export class CategoriesService {
  constructor(private readonly prisma: PrismaService) {}

  async findAll(getCategoryDto: GetCategoryDto) {
    return this.prisma.categories.findMany({
      where: {
        level: getCategoryDto.level,
      },
      include: {
        children: true,
      },
    });
  }

  async findProducts(id: string, getCategoryProductDto: GetCategoryProductDto) {
    const { page, perPage, sort, includeSubcategories } = getCategoryProductDto;
    const categoryId = Number.isNaN(parseInt(id)) ? 0 : parseInt(id);

    if (categoryId === 0) {
      throw new BadRequestException('🔴 카테고리 ID가 유효하지 않습니다.');
    }
    /**
     * @comment 데이터가 적기 때문에 카테고리 조회 후 상품 조회. 그리고 페이지네이션을 주기 편리함.
     */
    const productCategory = await this.prisma.product_categories.findMany({
      where: {
        category_id: categoryId,
      },
    });

    const productIds = productCategory.map((product) => product.product_id);

    if (includeSubcategories) {
      const subcategories = await this.prisma.categories.findMany({
        where: { parent_id: categoryId },
        select: { id: true },
      });
      if (subcategories.length > 0) {
        const subcategoryIds = subcategories.map((subcat) => subcat.id);
        const subProductCategories =
          await this.prisma.product_categories.findMany({
            where: {
              category_id: { in: subcategoryIds },
            },
          });
        const subProductIds = subProductCategories.map((pc) => pc.product_id);
        productIds.push(...subProductIds);
      }
    }
    // Parse sort parameter safely
    let orderField = 'created_at';
    let orderDirection: 'asc' | 'desc' = 'desc';

    if (sort && sort.includes(':')) {
      const [field, direction] = sort.split(':');
      orderField = field || 'created_at';
      orderDirection = direction === 'asc' ? 'asc' : 'desc';
    }

    const products = await this.prisma.products.findMany({
      where: {
        id: { in: productIds },
      },
      orderBy: {
        [orderField]: orderDirection,
      },
      skip: (page - 1) * perPage,
      take: perPage,
    });

    return products;
  }
}
