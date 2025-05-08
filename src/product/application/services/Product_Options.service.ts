import { Inject, Injectable, NotFoundException } from "@nestjs/common";

import { IBaseRepository } from "src/libs/domain/repositories";
import { Product_Image, Product_Option } from "src/product/domain/entities";

@Injectable()
export default class ProductOptionsService {
  constructor(
    @Inject("IProductOptionsRepository")
    private readonly repository: IBaseRepository<Product_Option>,
    @Inject("IProductImageRepository")
    private readonly product_image_repository: IBaseRepository<Product_Image>,
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
    const is_updated = await this.repository.update(options, option_id);

    if (!is_updated) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Option", resourceId: option_id },
      });
    }

    return (await this.repository.find_by_id(option_id))!;
  }

  async remove(product_id: number, option_id: number): Promise<void> {
    const is_deleted = await this.repository.delete(option_id);

    if (!is_deleted) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Option", resourceId: option_id },
      });
    }
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
