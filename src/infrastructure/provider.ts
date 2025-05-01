import { TypeOrmModuleOptions } from "@nestjs/typeorm";

import {
  CategoryRepository,
  MainRepository,
  ProductCategoryRepository,
  ProductDetailRepository,
  ProductImageRepository,
  ProductOptionGroupRepository,
  ProductOptionsRepository,
  ProductPriceRepository,
  ProductRepository,
  ProductTagRepository,
  ReviewRepository,
} from "./repositories";

export const type_orm_config = {
  useFactory: (): TypeOrmModuleOptions => {
    const { DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD, DB_DATABASE } = process.env;

    return {
      type: "postgres",
      host: DB_HOST,
      port: Number(DB_PORT),
      username: DB_USERNAME,
      password: DB_PASSWORD,
      database: DB_DATABASE,
      entities: [__dirname + "/**/*.entity.js", __dirname + "/**/*.view.js"],
      synchronize: true, // 개발
    };
  },
};

export const repository_providers = [
  { provide: "IProductRepository", useClass: ProductRepository },
  { provide: "IProductOptionsRepository", useClass: ProductOptionsRepository },
  { provide: "IProductImageRepository", useClass: ProductImageRepository },
  { provide: "IReviewRepository", useClass: ReviewRepository },
  { provide: "ICategoryRepository", useClass: CategoryRepository },
  { provide: "IMainRepository", useClass: MainRepository },
  { provide: "IProductDetailRepository", useClass: ProductDetailRepository },
  { provide: "IProductPriceRepository", useClass: ProductPriceRepository },
  { provide: "IProductCategoryRepository", useClass: ProductCategoryRepository },
  { provide: "IProductOptionGroupRepository", useClass: ProductOptionGroupRepository },
  { provide: "IProductTagRepository", useClass: ProductTagRepository },
];
