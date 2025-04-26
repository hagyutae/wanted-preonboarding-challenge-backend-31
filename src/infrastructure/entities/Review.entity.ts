import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  CreateDateColumn,
  UpdateDateColumn,
} from "typeorm";

import { ProductEntity } from "./Product.entity";
import { UserEntity } from "./User.entity";

@Entity("reviews")
export class ReviewEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, { onDelete: "CASCADE" })
  product: ProductEntity;

  @ManyToOne(() => UserEntity, { onDelete: "SET NULL", nullable: true })
  user: UserEntity;

  @Column({ type: "int", nullable: false })
  rating: number;

  @Column({ type: "varchar", length: 255, nullable: true })
  title: string;

  @Column({ type: "text", nullable: true })
  content: string;

  @CreateDateColumn({ name: "created_at" })
  createdAt: Date;

  @UpdateDateColumn({ name: "updated_at" })
  updatedAt: Date;

  @Column({ type: "boolean", default: false })
  verifiedPurchase: boolean;

  @Column({ type: "int", default: 0 })
  helpfulVotes: number;
}
