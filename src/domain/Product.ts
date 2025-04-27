import Seller from "./Seller";
import Brand from "./Brand";

export default class Product {
  constructor(
    public name: string,
    public slug: string,
    public short_description: string,
    public full_description: string,
    public created_at: Date,
    public updated_at: Date,
    public seller: Seller,
    public brand: Brand,
    public status: string,
    public id?: number,
  ) {}
}
