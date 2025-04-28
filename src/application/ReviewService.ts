import { Injectable } from "@nestjs/common";
import { ReviewEntity } from "src/infrastructure/entities";
import { EntityManager } from "typeorm";

@Injectable()
export default class ReviewService {
  constructor(private readonly entityManager: EntityManager) {}

  async get(
    product_id: number,
    {
      page = 1,
      perPage = 10,
      sort,
      rating,
    }: {
      page?: number;
      perPage?: number;
      sort?: string;
      rating?: number;
    },
  ) {
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.entityManager
      .getRepository(ReviewEntity)
      .createQueryBuilder("reviews")
      .where("1 = 1")
      .andWhere("reviews.product_id = :product_id", { product_id })
      .andWhere(rating ? "reviews.rating = :rating" : "1=1", { rating })
      .orderBy(`reviews.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * perPage)
      .take(perPage);

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
        total_pages: Math.ceil(reviews.length / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  }

  async create(product_id: number, review: Partial<ReviewEntity>) {
    const reviewEntity = this.entityManager.create(ReviewEntity, {
      ...review,
      product: { id: product_id },
    });
    return this.entityManager.save(reviewEntity);
  }

  async update(id: number, review: Partial<ReviewEntity>) {
    await this.entityManager.update(ReviewEntity, id, review);

    const updatedReview = await this.entityManager.findOne(ReviewEntity, { where: { id } });
    return updatedReview;
  }

  async delete(id: number) {
    await this.entityManager.delete(ReviewEntity, id);
  }
}
