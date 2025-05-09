import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn } from "typeorm";

import ProductEntity from "./Product.entity";

@Entity("product_details")
export default class ProductDetailEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @OneToOne(() => ProductEntity, (product) => product.id, {
    onDelete: "CASCADE",
  })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column("decimal", { precision: 10, scale: 2, nullable: true })
  weight: number;

  @Column("jsonb", { nullable: true })
  dimensions: {
    width: number;
    height: number;
    depth: number;
  };

  @Column("text", { nullable: true })
  materials: string;

  @Column("varchar", { length: 100, nullable: true })
  country_of_origin: string;

  @Column("text", { nullable: true })
  warranty_info: string;

  @Column("text", { nullable: true })
  care_instructions: string;

  @Column("jsonb", { nullable: true })
  additional_info: {
    assembly_required: boolean;
    assembly_time: string;
  };
}
