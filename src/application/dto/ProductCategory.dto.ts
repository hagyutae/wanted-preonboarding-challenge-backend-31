export default class ProductCategoryDTO {
  constructor(
    public is_primary: boolean,
    public name?: string,
    public slug?: string,
    public category_id?: number,
    public product_id?: number,
    public id?: number,
  ) {}
}
