import Product_Option from "@product/domain/entities/Product_Option";

export default class ProductOptionGroupDTO {
  constructor(
    public name: string,
    public display_order: number,
    public options?: Omit<Product_Option, "option_group_id">[],
    public product_id?: number,
    public id?: number,
  ) {}
}
