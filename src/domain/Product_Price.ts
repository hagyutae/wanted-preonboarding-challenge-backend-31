import Product from "./Product";

export default class Product_Price {
  constructor(
    public id: string,
    public product: Product,
    public best_price: number,
    public sale_price: number,
    public cost_price: number,
    public currency: string,
    public tax_rate: number
  ) {}
}
