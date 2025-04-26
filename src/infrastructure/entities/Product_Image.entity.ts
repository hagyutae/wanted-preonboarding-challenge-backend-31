import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";

import ProductEntity from "./Product.entity";
import ProductOptionEntity from "./Product_Option.entity";

@Entity("product_images")
export default class ProductImageEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column({ type: "varchar", length: 255 })
  url: string;

  @Column({ type: "varchar", length: 255, nullable: true })
  alt_text: string;

  @Column({ type: "boolean", default: false })
  is_primary: boolean;

  @Column({ type: "int", default: 0 })
  display_order: number;

  @ManyToOne(() => ProductOptionEntity, {
    onDelete: "SET NULL",
    nullable: true,
  })
  @JoinColumn({ name: "option_id" })
  option: ProductOptionEntity;
}
