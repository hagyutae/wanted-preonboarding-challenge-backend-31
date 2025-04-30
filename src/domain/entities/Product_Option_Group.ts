import Product_Option from "./Product_Option";

export default class Product_Option_Group {
  constructor(
    public name: string,
    public display_order: number,
    public options: Product_Option[],
    public product_id: number,
    public id?: number,
  ) {}
}
