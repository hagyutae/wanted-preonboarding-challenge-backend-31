import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { ProductOptionGroupEntity } from "./Product_Option_Group.entity";

@Entity("product_options")
export class ProductOptionEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @ManyToOne(() => ProductOptionGroupEntity, { onDelete: "CASCADE" })
  @JoinColumn({ name: "option_group_id" })
  optionGroup: ProductOptionGroupEntity;

  @Column({ type: "varchar", length: 100 })
  name: string;

  @Column({ type: "decimal", precision: 12, scale: 2, default: 0 })
  additionalPrice: number;

  @Column({ type: "varchar", length: 100, nullable: true })
  sku: string;

  @Column({ type: "int", default: 0 })
  stock: number;

  @Column({ type: "int", default: 0 })
  displayOrder: number;
}
