import { Inject, Injectable } from "@nestjs/common";

import { IBrowsingRepository } from "@libs/domain/repositories";

@Injectable()
export default class BrowsingService {
  constructor(
    @Inject("IBrowsingRepository")
    private readonly repository: IBrowsingRepository,
  ) {}

  async find() {
    const new_products = await this.repository.find_by_filters({
      sort_field: "created_at",
      sort_order: "DESC",
    });

    const popular_products = await this.repository.find_by_filters({
      sort_field: "rating",
      sort_order: "DESC",
    });

    const featured_categories = await this.repository.get_featured_categories();

    return {
      new_products,
      popular_products,
      featured_categories,
    };
  }
}
