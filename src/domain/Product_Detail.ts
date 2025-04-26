export default class Product_Detail {
  constructor(
    public id: string,
    public weight: number,
    public dimensions: string,
    public materials: string,
    public country_of_origin: string,
    public warranty_info: string,
    public care_instructions: string,
    public additional_info: string
  ) {}
}
