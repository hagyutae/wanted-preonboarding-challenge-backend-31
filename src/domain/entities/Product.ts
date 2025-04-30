export default class Product {
  constructor(
    public name: string,
    public slug: string,
    public short_description: string,
    public full_description: string,
    public status: string,
    public seller_id: number,
    public brand_id: number,
    public id?: number,
    public created_at?: Date,
    public updated_at?: Date,
  ) {}
}
