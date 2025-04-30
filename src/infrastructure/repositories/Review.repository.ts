import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Review } from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ReviewEntity } from "src/infrastructure/entities";

@Injectable()
export default class ReviewRepository implements IRepository<Review, ReviewEntity> {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({ product_id, ...review }: Review): Promise<ReviewEntity> {
    const review_entity = this.entity_manager.create(ReviewEntity, {
      ...review,
      product: { id: product_id },
    });
    return this.entity_manager.save(review_entity);
  }

  async find_by_filters({
    product_id,
    page = 1,
    per_page = 10,
    sort_field,
    sort_order = "DESC",
    rating,
  }: {
    product_id: number;
    page?: number;
    per_page?: number;
    sort_field?: string;
    sort_order?: string;
    rating?: number;
  }): Promise<ReviewEntity[]> {
    const query = this.entity_manager
      .getRepository(ReviewEntity)
      .createQueryBuilder("reviews")
      .leftJoinAndSelect("reviews.user", "user")
      .where("1 = 1")
      .andWhere("reviews.product_id = :product_id", { product_id })
      .andWhere(rating ? "reviews.rating = :rating" : "1=1", { rating })
      .orderBy(`reviews.${sort_field}`, sort_order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * per_page)
      .take(per_page);

    return await query.getMany();
  }

  async update(review: Omit<Review, "product_id">, id: number) {
    await this.entity_manager.update(ReviewEntity, id, review);
  }

  async delete(id: number): Promise<void> {
    await this.entity_manager.delete(ReviewEntity, id);
  }

  saves(param: Review[]): Promise<ReviewEntity[]> {
    throw new Error("Method not implemented.");
  }
  find_by_id(id: number): Promise<ReviewEntity | null> {
    throw new Error("Method not implemented.");
  }
}
