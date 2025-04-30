import {
  Product,
  Product_Detail,
  Product_Price,
  Product_Category,
  Product_Option_Group,
  Product_Image,
} from "src/domain/entities";

type ProductInputDTO = {
  detail: Product_Detail;
  price: Product_Price;
  categories: Product_Category[];
  option_groups: Product_Option_Group[];
  images: Product_Image[];
  tags: number[];
  seller_id: number;
  brand_id: number;
} & Product;

export default ProductInputDTO;
