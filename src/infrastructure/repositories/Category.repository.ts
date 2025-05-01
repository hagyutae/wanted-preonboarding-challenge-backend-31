import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import IRepository from "src/domain/repositories/IRepository";
import { CategoryEntity } from "src/infrastructure/entities";

@Injectable()
export default class CategoryRepository implements IRepository<CategoryEntity, CategoryEntity> {
  constructor(private readonly entity_manager: EntityManager) {}

  async find_by_filters() {
    return this.entity_manager.find(CategoryEntity, {
      relations: ["parent"],
    });
  }

  async find_by_id(category_id: number, has_sub?: boolean) {
    return this.entity_manager.findOne(CategoryEntity, {
      where: { id: category_id },
      relations: has_sub ? ["parent"] : undefined,
    });
  }

  save(param: CategoryEntity): Promise<CategoryEntity> {
    throw new Error("Method not implemented.");
  }
  saves(param: CategoryEntity[]): Promise<CategoryEntity[]> {
    throw new Error("Method not implemented.");
  }
  update(param: CategoryEntity, id: number): Promise<void | CategoryEntity> {
    throw new Error("Method not implemented.");
  }
  delete(id: number): Promise<void> {
    throw new Error("Method not implemented.");
  }
}
