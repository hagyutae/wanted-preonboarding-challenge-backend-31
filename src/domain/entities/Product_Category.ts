export default class Product_Category {
  constructor(
    public is_primary: boolean,
    public category_id?: number,
    public product_id?: number,
    public id?: number,
  ) {}
}
