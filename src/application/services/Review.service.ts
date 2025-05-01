import { Inject, Injectable, NotFoundException } from "@nestjs/common";

import { Review } from "src/domain/entities";
import { IRepository } from "src/domain/repositories";
import { FilterDTO } from "../dto";

@Injectable()
export default class ReviewService {
  constructor(
    @Inject("IReviewRepository")
    private readonly repository: IRepository<Review>,
  ) {}

  async find(product_id: number, { page = 1, per_page = 10, sort, rating }: FilterDTO) {
    const [sort_field, sort_order] = sort?.split(":") ?? ["created_at", "DESC"];

    const reviews = await this.repository.find_by_filters({
      product_id,
      page,
      per_page,
      sort_field,
      sort_order,
      rating,
    });

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

  async register(product_id: number, review: Omit<Review, "product_id">) {
    return await this.repository.save({ product_id, ...review });
  }

  async edit(id: number, review: Omit<Review, "product_id">) {
    const is_updated = await this.repository.update(review, id);

    if (!is_updated) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Review", resourceId: id },
      });
    }

    return await this.repository.find_by_id(id);
  }

  async remove(id: number) {
    const is_deleted = await this.repository.delete(id);

    if (!is_deleted) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Review", resourceId: id },
      });
    }
  }
}
