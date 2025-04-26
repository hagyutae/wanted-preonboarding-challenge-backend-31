import Product from "./Product";
import Option from "./Product_Option";

export default class Product_Image {
  constructor(
    public id: string,
    public product: Product,
    public option: Option,
    public url: string,
    public alt_text: string,
    public is_primary: boolean,
    public display_order: number
  ) {}
}
