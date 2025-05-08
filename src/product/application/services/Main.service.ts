import { Inject, Injectable } from "@nestjs/common";

import { IMainRepository } from "@libs/domain/repositories";

@Injectable()
export default class MainService {
  constructor(
    @Inject("IMainRepository")
    private readonly repository: IMainRepository,
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
