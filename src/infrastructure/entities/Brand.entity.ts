import { Entity, PrimaryGeneratedColumn, Column, Unique } from "typeorm";

@Entity("brands")
@Unique(["slug"])
export default class BrandEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @Column({ type: "varchar", length: 100, nullable: false })
  name: string;

  @Column({ type: "varchar", length: 100, unique: true, nullable: false })
  slug: string;

  @Column({ type: "text", nullable: true })
  description?: string;

  @Column({ type: "varchar", length: 255, nullable: true })
  logoUrl?: string;

  @Column({ type: "varchar", length: 255, nullable: true })
  website?: string;
}
