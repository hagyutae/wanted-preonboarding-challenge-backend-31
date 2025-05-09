import { Inject, Injectable } from "@nestjs/common";

import { IBrowsingRepository } from "@libs/domain/repositories";

@Injectable()
export default class BrowsingService {
  constructor(
    @Inject("IBrowsingRepository")
    private readonly repository: IBrowsingRepository,
  ) {}

  async find() {
    const page = 1;
    const per_page = 5;

    const new_products = await this.repository.get_new_products(page, per_page);

    const popular_products = await this.repository.get_popular_products();

    const featured_categories = await this.repository.get_featured_categories();

    return {
      new_products,
      popular_products,
      featured_categories,
    };
  }
}
