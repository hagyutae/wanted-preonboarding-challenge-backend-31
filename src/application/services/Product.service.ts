import { Injectable } from "@nestjs/common";

import { ProductRepository } from "src/infrastructure/repositories";
import { FilterDTO, ProductInputDTO } from "../dto";

@Injectable()
export default class ProductService {
  constructor(private readonly repository: ProductRepository) {}

  async register({
    detail,
    price,
    categories,
    option_groups,
    images,
    tags: tag_ids,
    seller_id,
    brand_id,
    ...product
  }: ProductInputDTO) {
    return this.repository.save({
      product,
      detail,
      price,
      categories,
      option_groups,
      images,
      tag_ids,
      seller_id,
      brand_id,
    });
  }

  async find_all({ page = 1, per_page = 10, sort, ...rest }: FilterDTO) {
    const [sort_field, sort_order] = sort?.split(":") ?? ["created_at", "DESC"];

    const items = await this.repository.find_by_filters({
      page,
      per_page,
      sort_field,
      sort_order,
      ...rest,
    });

    // 페이지네이션 요약 정보
    const pagination = {
      total_items: items.length,
      total_pages: Math.ceil(items.length / (per_page ?? 10)),
      current_page: page ?? 1,
      per_page: per_page ?? 10,
    };

    return { items, pagination };
  }

  async find(id: number) {
    return this.repository.get_by_id(id);
  }

  async edit(
    id: number,
    { detail, seller_id, brand_id, price, categories, ...product }: ProductInputDTO,
  ) {
    const updated_product_entity = await this.repository.update(id, {
      product,
      seller_id,
      brand_id,
      detail,
      price,
      categories,
    });

    return (({ id, name, slug, updated_at }) => ({
      id,
      name,
      slug,
      updated_at,
    }))(updated_product_entity);
  }

  async remove(id: number) {
    return this.repository.delete(id);
  }
}
