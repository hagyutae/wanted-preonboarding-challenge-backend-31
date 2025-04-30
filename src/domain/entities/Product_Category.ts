export default class Product_Category {
  constructor(
    public category_id: number,
    public product_id: number,
    public is_primary: boolean,
    public id?: number,
  ) {}
}
