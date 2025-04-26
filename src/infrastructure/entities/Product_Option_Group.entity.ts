import { Entity, PrimaryGeneratedColumn, Column, ManyToOne } from "typeorm";

import { Product } from "./Product.entity";

@Entity("product_option_groups")
export class ProductOptionGroup {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => Product, { onDelete: "CASCADE" })
  product: Product;

  @Column({ type: "varchar", length: 100 })
  name: string;

  @Column({ type: "int", default: 0 })
  displayOrder: number;
}
