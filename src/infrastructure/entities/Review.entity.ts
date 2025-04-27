import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  CreateDateColumn,
  UpdateDateColumn,
} from "typeorm";

import ProductEntity from "./Product.entity";
import UserEntity from "./User.entity";

@Entity("reviews")
export default class ReviewEntity {
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
  created_at: Date;

  @UpdateDateColumn({ name: "updated_at" })
  updated_at: Date;

  @Column({ type: "boolean", default: false })
  verified_purchase: boolean;

  @Column({ type: "int", default: 0 })
  helpful_votes: number;
}
