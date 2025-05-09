export default class Product {
  constructor(
    public name: string,
    public slug: string,
    public status: string,
    public short_description?: string,
    public full_description?: string,
    public seller_id?: number,
    public brand_id?: number,
    public id?: number,
    public created_at?: Date,
    public updated_at?: Date,
  ) {}
}
