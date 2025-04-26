import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  CreateDateColumn,
} from "typeorm";

@Entity("sellers")
export class Seller {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @Column({ type: "varchar", length: 100, nullable: false })
  name: string;

  @Column({ type: "text", nullable: true })
  description: string;

  @Column({ type: "varchar", length: 255, nullable: true })
  logoUrl: string;

  @Column({ type: "decimal", precision: 3, scale: 2, nullable: true })
  rating: number;

  @Column({ type: "varchar", length: 100, nullable: true })
  contactEmail: string;

  @Column({ type: "varchar", length: 20, nullable: true })
  contactPhone: string;

  @CreateDateColumn({ type: "timestamp", default: () => "CURRENT_TIMESTAMP" })
  createdAt: Date;
}
