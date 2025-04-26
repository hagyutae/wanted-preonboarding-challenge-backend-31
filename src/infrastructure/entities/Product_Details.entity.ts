import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { ProductEntity } from "./Product.entity";

@Entity("product_details")
export class ProductDetailsEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, (product) => product.id, {
    onDelete: "CASCADE",
  })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column("decimal", { precision: 10, scale: 2, nullable: true })
  weight: number;

  @Column("jsonb", { nullable: true })
  dimensions: object;

  @Column("text", { nullable: true })
  materials: string;

  @Column("varchar", { length: 100, nullable: true })
  country_of_origin: string;

  @Column("text", { nullable: true })
  warranty_info: string;

  @Column("text", { nullable: true })
  care_instructions: string;

  @Column("jsonb", { nullable: true })
  additional_info: object;
}
