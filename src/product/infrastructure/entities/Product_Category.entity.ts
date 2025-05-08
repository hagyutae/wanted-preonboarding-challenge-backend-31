import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";

import { CategoryEntity } from "@category/infrastructure/entities";
import ProductEntity from "./Product.entity";

@Entity("product_categories")
export default class ProductCategoryEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @ManyToOne(() => CategoryEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "category_id" })
  category: CategoryEntity;

  @Column({ type: "boolean", default: false })
  is_primary: boolean;
}
