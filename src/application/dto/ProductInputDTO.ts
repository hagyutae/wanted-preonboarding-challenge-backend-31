import {
  Product_Detail,
  Product_Price,
  Product_Category,
  Product_Option_Group,
  Product_Image,
  Product,
} from "src/domain";

export type ProductInputDTO = {
  detail: Product_Detail;
  price: Product_Price;
  categories: Product_Category[];
  option_groups: Product_Option_Group[];
  images: Product_Image[];
  tags: number[];
  seller_id: number;
  brand_id: number;
} & Product;
