import { Injectable } from '@nestjs/common';
import { CategoryRepository } from './categories.repository';
import { GetProductsByCategoryIdRequestDto } from './dto/category.dto';

@Injectable()
export class CategoriesService {
  constructor(private readonly categoryRepository: CategoryRepository) {}

  async getCategories(level?: number) {
    return this.categoryRepository.getCategories(level);
  }

  async getProductsByCategoryId(
    id: number,
    query: GetProductsByCategoryIdRequestDto,
  ) {
    return this.categoryRepository.getProductsByCategoryId(id, query);
  }
}
