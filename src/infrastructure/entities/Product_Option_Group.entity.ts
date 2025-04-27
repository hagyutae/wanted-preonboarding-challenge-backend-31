import { Entity, PrimaryGeneratedColumn, Column, ManyToOne } from "typeorm";

import ProductEntity from "./Product.entity";

@Entity("product_option_groups")
export default class ProductOptionGroupEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  product: ProductEntity;

  @Column({ type: "varchar", length: 100 })
  name: string;

  @Column({ type: "int", default: 0 })
  display_order: number;
}
