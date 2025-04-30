import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { CategoryEntity } from "src/infrastructure/entities";
import { FilterDTO } from "../dto";

@Injectable()
export default class CategoryService {
  constructor(private readonly entityManager: EntityManager) {}

  public build_tree(
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
        children: this.build_tree(categories, level + 1, category.id),
      }));

    return result;
  }

  async get_all_categories_as_tree(level: number = 1) {
    const categories = await this.entityManager.find(CategoryEntity, {
      relations: ["parent"],
    });

    return this.build_tree(categories, level);
  }

  async get_products_by_category_id(
    category_id: number,
    { page = 1, per_page = 10, sort = "created_at:desc", has_sub = true }: FilterDTO,
  ) {
    const category = await this.entityManager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: has_sub ? ["parent"] : undefined,
    });

    const [field, order] = sort.split(":");

    const query = this.entityManager
      .getRepository(CategoryEntity)
      .createQueryBuilder("categories")
      .where("1 = 1")
      .andWhere("categories.id = :category_id", { category_id })
      .orderBy(`products.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * per_page)
      .take(per_page);

    const items = await query.getMany();

    return {
      category,
      items,
      pagination: {
        total_items: items.length,
        total_pages: Math.ceil(items.length / per_page),
        current_page: page,
        per_page: per_page,
      },
    };
  }
}
