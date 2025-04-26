import Product from "./Product";
import Tag from "./Tag";

export default class Product_Tag {
  constructor(
    public id: string,
    public product: Product,
    public tag: Tag
  ) {}
}
