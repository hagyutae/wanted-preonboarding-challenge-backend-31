export default class Product_Detail {
  constructor(
    public weight: number,
    public dimensions: {
      width: number;
      height: number;
      depth: number;
    },
    public materials: string,
    public country_of_origin: string,
    public warranty_info: string,
    public care_instructions: string,
    public additional_info: {
      assembly_required: boolean;
      assembly_time: string;
    },
    public product_id?: number,
    public id?: number,
  ) {}
}
