import { Entity, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from "typeorm";

import { Product } from "./Product.entity";
import { Tag } from "./Tag.entity";

@Entity("product_tags")
export class ProductTag {
  @PrimaryGeneratedColumn("increment")
  id: number;

  @ManyToOne(() => Product, (product) => product.id, { onDelete: "CASCADE" })
  @JoinColumn({ name: "product_id" })
  product: Product;

  @ManyToOne(() => Tag, (tag) => tag.id, { onDelete: "CASCADE" })
  @JoinColumn({ name: "tag_id" })
  tag: Tag;
}
