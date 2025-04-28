import { EntityManager } from "typeorm";
import { Injectable } from "@nestjs/common";

import {
  ProductEntity,
  ProductPriceEntity,
  ReviewEntity,
  BrandEntity,
  SellerEntity,
  CategoryEntity,
} from "src/infrastructure/entities";

@Injectable()
export default class MainService {
  constructor(private readonly entityManager: EntityManager) {}

  async getNewProducts() {
    const query = { page: 1, perPage: 5 };

    const products = await this.entityManager.find(ProductEntity, {
      order: { created_at: "DESC" },
      skip: (query.page - 1) * query.perPage,
      take: query.perPage,
    });

    return products;
  }

  async getPopularProducts() {
    throw new Error("Not implemented");
  }

  async getFeaturedCategories() {
    throw new Error("Not implemented");
  }
}
