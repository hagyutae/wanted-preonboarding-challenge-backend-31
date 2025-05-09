export default class Product_Price {
  constructor(
    public base_price: number,
    public sale_price: number,
    public cost_price: number,
    public currency: string,
    public tax_rate: number,
    public product_id?: number,
    public id?: number,
  ) {}
}
