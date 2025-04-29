import { DataSource, ViewColumn, ViewEntity } from "typeorm";

import { ProductOptionGroupEntity, ProductOptionEntity } from "../entities";

@ViewEntity({
  expression: (dataSource: DataSource) =>
    dataSource
      .getRepository(ProductOptionGroupEntity)
      .createQueryBuilder("product_option_groups")
      .leftJoin(
        ProductOptionEntity,
        "product_options",
        "product_options.option_group_id = product_option_groups.id",
      )
      .select("product_option_groups.product_id", "product_id")
      .addSelect("CASE WHEN SUM(product_options.stock) > 0 THEN true ELSE false END", "in_stock")
      .groupBy("product_option_groups.product_id"),
  materialized: true,
})
export default class ProductStockView {
  @ViewColumn()
  product_id: number;

  @ViewColumn()
  in_stock: boolean;
}
