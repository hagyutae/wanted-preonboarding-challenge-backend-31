import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { BaseRepository } from "src/libs/domain/repositories";
import { Category } from "src/product/domain/entities";
import { CategoryEntity } from "src/product/infrastructure/entities";

@Injectable()
export default class CategoryRepository extends BaseRepository<Category> {
  constructor(protected readonly entity_manager: EntityManager) {
    super(entity_manager);
  }

  async find_by_filters() {
    return this.entity_manager.find(CategoryEntity, {
      relations: ["parent"],
    });
  }

  async find_by_id(category_id: number) {
    return this.entity_manager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: ["parent"],
    });
  }
}
