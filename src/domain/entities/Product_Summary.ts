export default class Product_Summary {
  constructor(
    public id: number,
    public name: string,
    public slug: string,
    public short_description: string,
    public base_price: number,
    public sale_price: number,
    public currency: string,
    public primary_image: { url: string; alt_text: string },
    public brand: { id: number; name: string },
    public seller: { id: number; name: string },
    public status: string,
    public in_stock: boolean,
    public rating: number,
    public review_count: number,
    public created_at: Date,
    public updated_at?: Date,
  ) {}
}
