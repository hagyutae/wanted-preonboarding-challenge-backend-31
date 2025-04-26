import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
  UpdateDateColumn,
  ManyToOne,
  JoinColumn,
} from "typeorm";
import { Seller } from "./Seller.entity";
import { Brand } from "./Brand.entity";

@Entity("products")
export class Product {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: string;

  @Column({ type: "varchar", length: 255 })
  name: string;

  @Column({ type: "varchar", length: 255, unique: true })
  slug: string;

  @Column({ type: "varchar", length: 500, nullable: true })
  shortDescription?: string;

  @Column({ type: "text", nullable: true })
  fullDescription?: string;

  @CreateDateColumn({
    type: "timestamp",
    name: "created_at",
    default: () => "CURRENT_TIMESTAMP",
  })
  createdAt: Date;

  @UpdateDateColumn({
    type: "timestamp",
    name: "updated_at",
    default: () => "CURRENT_TIMESTAMP",
  })
  updatedAt: Date;

  @ManyToOne(() => Seller)
  @JoinColumn({ name: "seller_id" })
  seller: Seller;

  @ManyToOne(() => Brand)
  @JoinColumn({ name: "brand_id" })
  brand: Brand;

  @Column({ type: "varchar", length: 20 })
  status: string;
}
