interface Dimensions {
  width: number;
  height: number;
  depth: number;
}

interface AdditionalInfo {
  assembly_required: boolean;
  assembly_time: string;
}

interface Detail {
  weight: number;
  dimensions: Dimensions;
  materials: string;
  country_of_origin: string;
  warranty_info: string;
  care_instructions: string;
  additional_info: AdditionalInfo;
}

interface Price {
  base_price: number;
  sale_price: number;
  cost_price: number;
  currency: string;
  tax_rate: number;
}

interface Category {
  category_id: number;
  is_primary: boolean;
}

interface Option {
  name: string;
  additional_price: number;
  sku: string;
  stock: number;
  display_order: number;
}

interface OptionGroup {
  name: string;
  display_order: number;
  options: Option[];
}

interface Image {
  url: string;
  alt_text: string;
  is_primary: boolean;
  display_order: number;
  option_id: number | null;
}

export default interface PostBodyDTO {
  name: string;
  slug: string;
  short_description: string;
  full_description: string;
  seller_id: number;
  brand_id: number;
  status: string;
  detail: Detail;
  price: Price;
  categories: Category[];
  option_groups: OptionGroup[];
  images: Image[];
  tags: number[];
}
