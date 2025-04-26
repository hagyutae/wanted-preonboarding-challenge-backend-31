import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from "typeorm";

@Entity("categories")
export default class CategoryEntity {
  @PrimaryGeneratedColumn("increment", { type: "bigint" })
  id: number;

  @Column({ type: "varchar", length: 100, nullable: false })
  name: string;

  @Column({ type: "varchar", length: 100, unique: true, nullable: false })
  slug: string;

  @Column({ type: "text", nullable: true })
  description: string;

  @ManyToOne(() => CategoryEntity, (category) => category.id, {
    nullable: true,
  })
  @JoinColumn({ name: "parent_id" })
  parent: CategoryEntity;

  @Column({ type: "integer", nullable: false })
  level: number;

  @Column({ type: "varchar", length: 255, nullable: true })
  imageUrl: string;
}
