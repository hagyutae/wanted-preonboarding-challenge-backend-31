import { Injectable } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain/entities";
import { ProductImageRepository, ProductOptionsRepository } from "src/infrastructure/repositories";

@Injectable()
export default class ProductOptionsService {
  constructor(
    private readonly repository: ProductOptionsRepository,
    private readonly product_image_repository: ProductImageRepository,
  ) {}

  async register(
    id: number,
    option_group_id: number,
    option: Omit<Product_Option, "option_group_id">,
  ): Promise<Product_Option> {
    return this.repository.save({ option_group_id, ...option });
  }

  async update(
    product_id: number,
    option_id: number,
    options: Omit<Product_Option, "option_group_id">,
  ): Promise<Product_Option> {
    return this.repository.update(options, option_id);
  }

  async remove(product_id: number, option_id: number): Promise<void> {
    await this.repository.delete(option_id);
  }

  async register_images(
    id: number,
    option_id: number,
    image: Omit<Product_Image, "product_id" | "option_id">,
  ) {
    const image_entity = await this.product_image_repository.save({
      product_id: id,
      option_id,
      ...image,
    });

    // 이미지 저장 결과 반환
    return (({ product, option, ...rest }) => ({ ...rest, option_id: option.id }))(image_entity);
  }
}
