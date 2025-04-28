import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { CategoryEntity } from "src/infrastructure/entities";

@Injectable()
export default class CategoryService {
  constructor(private readonly entityManager: EntityManager) {}

  public buildTree(
    categories: CategoryEntity[],
    level: number, // 1: 대분류, 2: 중분류, 3: 소분류
    parent_id?: number,
  ) {
    if (level > 3) {
      return [];
    }

    const result = categories
      .filter((category) => category.parent?.id === parent_id)
      .map((category) => ({
        ...category,
        children: this.buildTree(categories, level + 1, category.id),
      }));

    return result;
  }

  async getAllCategoriesAsTree(level: number = 1) {
    const categories = await this.entityManager.find(CategoryEntity, {
      relations: ["parent"],
    });

    return this.buildTree(categories, level);
  }
}
