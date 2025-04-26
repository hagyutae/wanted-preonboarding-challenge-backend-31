import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { Product } from "./Product.entity";
import { Category } from "./Category.entity";

@Entity("product_categories")
export class ProductCategory {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: string;

  @ManyToOne(() => Product, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: Product;

  @ManyToOne(() => Category, { onDelete: "CASCADE" })
  @JoinColumn({ name: "category_id" })
  category: Category;

  @Column({ type: "boolean", default: false, name: "is_primary" })
  isPrimary: boolean;
}
