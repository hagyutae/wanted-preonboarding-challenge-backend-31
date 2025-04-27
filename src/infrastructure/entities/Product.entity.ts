import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  ManyToOne,
  JoinColumn,
} from "typeorm";

import SellerEntity from "./Seller.entity";
import BrandEntity from "./Brand.entity";

@Entity("products")
export default class ProductEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: string;

  @Column({ type: "varchar", length: 255 })
  name: string;

  @Column({ type: "varchar", length: 255, unique: true })
  slug: string;

  @Column({ type: "varchar", length: 500, nullable: true })
  short_description?: string;

  @Column({ type: "text", nullable: true })
  full_description?: string;

  @CreateDateColumn({
    type: "timestamp",
    name: "created_at",
    default: () => "CURRENT_TIMESTAMP",
  })
  created_at: Date;

  @UpdateDateColumn({
    type: "timestamp",
    name: "updated_at",
    default: () => "CURRENT_TIMESTAMP",
  })
  updated_at: Date;

  @ManyToOne(() => SellerEntity)
  @JoinColumn({ name: "seller_id" })
  seller: SellerEntity;

  @ManyToOne(() => BrandEntity)
  @JoinColumn({ name: "brand_id" })
  brand: BrandEntity;

  @Column({ type: "varchar", length: 20 })
  status: string;
}
