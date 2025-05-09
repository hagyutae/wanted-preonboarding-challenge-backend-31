import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";

import ProductEntity from "./Product.entity";

@Entity("product_option_groups")
export default class ProductOptionGroupEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column({ type: "varchar", length: 100 })
  name: string;

  @Column({ type: "int", default: 0 })
  display_order: number;
}
