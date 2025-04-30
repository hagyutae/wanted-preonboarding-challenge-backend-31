import {
  Product,
  Product_Detail,
  Product_Price,
  Product_Category,
  Product_Option_Group,
  Product_Image,
} from "src/domain/entities";

type ProductInputDTO = {
  detail: Omit<Product_Detail, "product_id">;
  price: Omit<Product_Price, "product_id">;
  categories: Omit<Product_Category, "product_id">[];
  option_groups: Omit<Product_Option_Group, "product_id">[];
  images: Omit<Product_Image, "product_id">[];
  tags: number[];
  seller_id: number;
  brand_id: number;
} & Product;

export default ProductInputDTO;
