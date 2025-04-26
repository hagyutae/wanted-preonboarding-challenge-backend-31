import Product from "./Product";

export default class Product_Option_Group {
  constructor(
    public id: string,
    public product: Product,
    public name: string,
    public display_order: number
  ) {}
}
