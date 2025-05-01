import { Inject, Injectable } from "@nestjs/common";

import { Product_Image, Product_Option } from "src/domain/entities";
import { IRepository } from "src/domain/repositories";

@Injectable()
export default class ProductOptionsService {
  constructor(
    @Inject("IProductOptionsRepository")
    private readonly repository: IRepository<Product_Option>,
    @Inject("IProductImageRepository")
    private readonly product_image_repository: IRepository<Product_Image>,
  ) {}

  async register(
    id: number,
    option_group_id: number,
    option: Omit<Product_Option, "option_group_id">,
  ): Promise<Product_Option> {
    return await this.repository.save({ option_group_id, ...option });
  }

  async update(
    product_id: number,
    option_id: number,
    options: Omit<Product_Option, "option_group_id">,
  ): Promise<Product_Option> {
    const updated_option = await this.repository.update(options, option_id);
    if (!updated_option) {
      throw new Error("Failed to update the product option.");
    }
    return updated_option;
  }

  async remove(product_id: number, option_id: number): Promise<void> {
    await this.repository.delete(option_id);
  }

  async register_images(
    id: number,
    option_id: number,
    image: Omit<Product_Image, "product_id" | "option_id">,
  ) {
    const saved_product_image = await this.product_image_repository.save({
      product_id: id,
      option_id,
      ...image,
    });

    // 이미지 저장 결과 반환
    return (({ product, option, ...rest }) => ({ ...rest, option_id: option?.id }))(
      saved_product_image,
    );
  }
}
