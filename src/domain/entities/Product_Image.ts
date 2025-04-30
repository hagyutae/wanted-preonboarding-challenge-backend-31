export default class Product_Image {
  constructor(
    public url: string,
    public alt_text: string,
    public is_primary: boolean,
    public display_order: number,
    public product_id: number,
    public option_id: number | null,
    public id?: number,
  ) {}
}
