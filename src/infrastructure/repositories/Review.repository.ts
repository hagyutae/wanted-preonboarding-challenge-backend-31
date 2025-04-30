import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { ReviewEntity } from "src/infrastructure/entities";

@Injectable()
export default class ReviewRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async find({
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
  }) {
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

  async save(product_id: number, review: Partial<ReviewEntity>) {
    const review_entity = this.entity_manager.create(ReviewEntity, {
      ...review,
      product: { id: product_id },
    });
    return this.entity_manager.save(review_entity);
  }

  async update(id: number, review: Partial<ReviewEntity>) {
    await this.entity_manager.update(ReviewEntity, id, review);

    const updated_review = await this.entity_manager.findOne(ReviewEntity, { where: { id } });
    return updated_review;
  }

  async delete(id: number) {
    await this.entity_manager.delete(ReviewEntity, id);
  }
}
