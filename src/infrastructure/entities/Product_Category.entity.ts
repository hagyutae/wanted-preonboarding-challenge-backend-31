import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { ProductEntity } from "./Product.entity";
import { CategoryEntity } from "./Category.entity";

@Entity("product_categories")
export class ProductCategoryEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: string;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @ManyToOne(() => CategoryEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "category_id" })
  category: CategoryEntity;

  @Column({ type: "boolean", default: false, name: "is_primary" })
  isPrimary: boolean;
}
