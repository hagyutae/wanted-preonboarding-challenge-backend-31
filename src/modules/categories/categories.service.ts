import { Injectable } from '@nestjs/common';

import { CategoryRepository } from './categories.repository';
import { GetProductsByCategoryIdRequestDto } from './dto/category.dto';
import { ProductWithRelations } from '../products/entities/product.entity';
import { CategoryWithRelations } from './entities/category.entity';

@Injectable()
export class CategoriesService {
  constructor(private readonly categoryRepository: CategoryRepository) {}

  async getCategories(level?: number): Promise<CategoryWithRelations[]> {
    return this.categoryRepository.getCategories(level);
  }

  async getCategoryById(id: number): Promise<CategoryWithRelations> {
    return this.categoryRepository.getCategoryById(id);
  }

  async getProductsByCategoryId(
    id: number,
    query: GetProductsByCategoryIdRequestDto,
  ): Promise<{ items: ProductWithRelations[]; total: number }> {
    const items = await this.categoryRepository.getProductsByCategoryId(
      id,
      query,
    );
    const total = await this.categoryRepository.getProductsCountByCategoryId(
      id,
      query,
    );
    return { items, total };
  }
}
