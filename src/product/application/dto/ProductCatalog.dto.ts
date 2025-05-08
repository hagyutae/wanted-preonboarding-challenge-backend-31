import {
  Brand,
  Product_Detail,
  Product_Image,
  Product_Price,
  Seller,
  Tag,
} from "@product/domain/entities";
import ProductCategoryDTO from "./ProductCategory.dto";
import ProductOptionGroupDTO from "./ProductOptionGroup.dto";

type ProductCatalogDTO = {
  id: number;
  name: string;
  slug: string;
  short_description: string;
  full_description: string;
  status: string;
  created_at: Date;
  updated_at: Date;

  seller: Seller;

  brand: Brand;

  detail: Product_Detail;

  price: Product_Price & { discount_percentage: number };

  categories: ProductCategoryDTO[];

  option_groups: ProductOptionGroupDTO[];

  images: Product_Image[];

  tags: Tag[];

  rating: {
    average: number;
    count: number;
    distribution: {
      "5": number;
      "4": number;
      "3": number;
      "2": number;
      "1": number;
    };
  };
};

export default ProductCatalogDTO;
