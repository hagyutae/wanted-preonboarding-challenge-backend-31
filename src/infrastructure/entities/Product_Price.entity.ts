import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";

import ProductEntity from "./Product.entity";

@Entity("product_prices")
export default class ProductPriceEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, (product) => product.id, {
    onDelete: "CASCADE",
  })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @Column("decimal", { precision: 12, scale: 2, nullable: false })
  basePrice: number;

  @Column("decimal", { precision: 12, scale: 2, nullable: true })
  salePrice: number;

  @Column("decimal", { precision: 12, scale: 2, nullable: true })
  costPrice: number;

  @Column("varchar", { length: 3, default: "KRW" })
  currency: string;

  @Column("decimal", { precision: 5, scale: 2, nullable: true })
  taxRate: number;
}
