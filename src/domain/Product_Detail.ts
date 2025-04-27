export default class Product_Detail {
  constructor(
    public weight: number,
    public dimensions: object,
    public materials: string,
    public country_of_origin: string,
    public warranty_info: string,
    public care_instructions: string,
    public additional_info: object,
    public id?: number,
  ) {}
}
