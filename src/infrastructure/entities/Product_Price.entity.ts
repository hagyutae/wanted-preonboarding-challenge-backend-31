import { Entity, PrimaryGeneratedColumn, Column, OneToOne, JoinColumn } from "typeorm";

import ProductEntity from "./Product.entity";

@Entity("product_prices")
export default class ProductPriceEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @OneToOne(() => ProductEntity, (product) => product.id, {
    onDelete: "CASCADE",
  })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column("decimal", { precision: 12, scale: 2, nullable: false })
  base_price: number;

  @Column("decimal", { precision: 12, scale: 2, nullable: true })
  sale_price: number;

  @Column("decimal", { precision: 12, scale: 2, nullable: true })
  cost_price: number;

  @Column("varchar", { length: 3, default: "KRW" })
  currency: string;

  @Column("decimal", { precision: 5, scale: 2, nullable: true })
  tax_rate: number;
}
