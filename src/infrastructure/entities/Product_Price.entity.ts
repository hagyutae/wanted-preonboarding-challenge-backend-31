import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import { Product } from "./Product.entity";

@Entity("product_prices")
export class ProductPrice {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => Product, (product) => product.id, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: Product;

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
