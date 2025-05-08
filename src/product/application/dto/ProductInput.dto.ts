import { Product, Product_Detail, Product_Image, Product_Price } from "@product/domain/entities";
import ProductCategoryDTO from "./ProductCategory.dto";
import ProductOptionGroupDTO from "./ProductOptionGroup.dto";

type ProductInputDTO = {
  detail: Omit<Product_Detail, "product_id">;
  price: Omit<Product_Price, "product_id">;
  categories: Omit<ProductCategoryDTO, "product_id">[];
  option_groups: Omit<ProductOptionGroupDTO, "product_id">[];
  images: Omit<Product_Image, "product_id">[];
  tags: number[];
  seller_id: number;
  brand_id: number;
} & Product;

export default ProductInputDTO;
