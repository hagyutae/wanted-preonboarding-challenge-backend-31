import Category from "./Category";
import Product from "./Product";

export default class Product_Category {
  constructor(
    public id: string,
    public product: Product,
    public category: Category,
    public is_primary: boolean,
  ) {}
}
