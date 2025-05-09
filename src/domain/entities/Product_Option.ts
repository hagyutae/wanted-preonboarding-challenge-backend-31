export default class Product_Option {
  constructor(
    public name: string,
    public additional_price: number,
    public sku: string,
    public stock: number,
    public display_order: number,
    public option_group_id?: number,
    public id?: number,
  ) {}
}
