import {
  ProductCategoryRepository,
  ProductDetailRepository,
  ProductImageRepository,
  ProductOptionGroupRepository,
  ProductOptionsRepository,
  ProductPriceRepository,
  ProductRepository,
  ProductTagRepository,
} from "./repositories";

export default [
  { provide: "IProductRepository", useClass: ProductRepository },
  { provide: "IProductDetailRepository", useClass: ProductDetailRepository },
  { provide: "IProductPriceRepository", useClass: ProductPriceRepository },
  { provide: "IProductCategoryRepository", useClass: ProductCategoryRepository },
  { provide: "IProductOptionsRepository", useClass: ProductOptionsRepository },
  { provide: "IProductOptionGroupRepository", useClass: ProductOptionGroupRepository },
  { provide: "IProductImageRepository", useClass: ProductImageRepository },
  { provide: "IProductTagRepository", useClass: ProductTagRepository },
];
