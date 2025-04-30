import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { ReviewEntity } from "src/infrastructure/entities";
import { FilterDTO } from "../dto";

@Injectable()
export default class ReviewService {
  constructor(private readonly entity_manager: EntityManager) {}

  async get(product_id: number, { page = 1, per_page = 10, sort, rating }: FilterDTO) {
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.entity_manager
      .getRepository(ReviewEntity)
      .createQueryBuilder("reviews")
      .where("1 = 1")
      .andWhere("reviews.product_id = :product_id", { product_id })
      .andWhere(rating ? "reviews.rating = :rating" : "1=1", { rating })
      .orderBy(`reviews.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * per_page)
      .take(per_page);

    const reviews = await query.getMany();
    const summary = {
      average_rating:
        reviews.map((review) => review.rating).reduce((a, b) => a + b, 0) / reviews.length || 0,
      total_count: reviews.length,
      distribution: {
        1: reviews.filter((review) => review.rating === 1).length,
        2: reviews.filter((review) => review.rating === 2).length,
        3: reviews.filter((review) => review.rating === 3).length,
        4: reviews.filter((review) => review.rating === 4).length,
        5: reviews.filter((review) => review.rating === 5).length,
      },
    };

    return {
      items: reviews,
      summary,
      pagination: {
        total_items: reviews.length,
        total_pages: Math.ceil(reviews.length / per_page),
        current_page: page,
        per_page: per_page,
      },
    };
  }

  async create(product_id: number, review: Partial<ReviewEntity>) {
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
