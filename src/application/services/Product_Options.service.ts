import { Injectable } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain/entities";
import ProductOptionsRepository from "src/infrastructure/repositories/Product_Options.repository";

@Injectable()
export default class ProductOptionsService {
  constructor(private readonly repository: ProductOptionsRepository) {}

  async register(
    id: number,
    option_group_id: number,
    options: Product_Option,
  ): Promise<Product_Option> {
    return this.repository.save(id, option_group_id, options);
  }

  async find(id: number, option_id: number, options: Product_Option): Promise<Product_Option> {
    return this.repository.update(id, option_id, options);
  }

  async edit(id: number, option_id: number): Promise<void> {
    await this.repository.delete(id, option_id);
  }

  async register_images(
    id: number,
    option_id: number,
    image: Product_Image,
  ): Promise<Product_Image> {
    const image_entity = await this.repository.save_images(id, option_id, image);

    // 이미지 저장 결과 반환
    return (({ product, option, ...rest }) => ({ ...rest }))(image_entity);
  }
}
