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

  async getProductsByCategoryId(
    category_id: number,
    {
      page = 1,
      perPage = 10,
      sort = "created_at:desc",
      includeSubcategories = true,
    }: {
      page?: number;
      perPage?: number;
      sort?: string;
      includeSubcategories?: boolean;
    },
  ) {
    const category = await this.entityManager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: includeSubcategories ? ["parent"] : undefined,
    });

    const [field, order] = sort.split(":");

    const query = this.entityManager
      .getRepository(CategoryEntity)
      .createQueryBuilder("categories")
      .where("1 = 1")
      .andWhere("categories.id = :category_id", { category_id })
      .orderBy(`products.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * perPage)
      .take(perPage);

    const items = await query.getMany();

    return {
      category,
      items,
      pagination: {
        total_items: items.length,
        total_pages: Math.ceil(items.length / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  }
}
