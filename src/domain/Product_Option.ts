import Product_Option_Group from "./Product_Option_Group";

export default class Product_Option {
  constructor(
    public id: string,
    public option_group: Product_Option_Group,
    public name: string,
    public additional_price: number,
    public sku: string,
    public stock: number,
    public display_order: number,
  ) {}
}
