import { Entity, PrimaryGeneratedColumn, Column, Unique } from "typeorm";

@Entity("tags")
@Unique(["slug"])
export class Tag {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @Column({ type: "varchar", length: 100, nullable: false })
  name: string;

  @Column({ type: "varchar", length: 100, unique: true, nullable: false })
  slug: string;
}
