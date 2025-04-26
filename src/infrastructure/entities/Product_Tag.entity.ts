import { Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";

import { ProductEntity } from "./Product.entity";
import { TagEntity } from "./Tag.entity";

@Entity("product_tags")
export class ProductTagEntity {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => ProductEntity, (product) => product.id, {
    onDelete: "CASCADE",
  })
  @JoinColumn({ name: "product_id" })
  product: ProductEntity;

  @ManyToOne(() => TagEntity, (tag) => tag.id, { onDelete: "CASCADE" })
  @JoinColumn({ name: "tag_id" })
  tag: TagEntity;
}
