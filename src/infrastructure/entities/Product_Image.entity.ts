import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { Product } from "./Product.entity";
import { ProductOption } from "./Product_Option.entity";

@Entity("product_images")
export class ProductImage {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => Product, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: Product;

  @Column({ type: "varchar", length: 255 })
  url: string;

  @Column({ type: "varchar", length: 255, nullable: true })
  alt_text: string;

  @Column({ type: "boolean", default: false })
  is_primary: boolean;

  @Column({ type: "int", default: 0 })
  display_order: number;

  @ManyToOne(() => ProductOption, { onDelete: "SET NULL", nullable: true })
  @JoinColumn({ name: "option_id" })
  option: ProductOption;
}
