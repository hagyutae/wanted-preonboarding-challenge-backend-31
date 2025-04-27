import { Repository } from "typeorm";
import { InjectRepository } from "@nestjs/typeorm";
import { Injectable } from "@nestjs/common";

import IRepository from "./IRepository";
import ProductEntity from "./entities/Product.entity";

@Injectable()
export default class ProductRepository implements IRepository<ProductEntity> {
  constructor(
    @InjectRepository(ProductEntity)
    private readonly repository: Repository<ProductEntity>,
  ) {}

  async create(data: ProductEntity): Promise<ProductEntity> {
    const entity = this.repository.create(data);
    return await this.repository.save(entity);
  }

  async findAll({
    status,
    search,
    sort,
    page = 1,
    perPage = 10,
  }: {
    page?: number;
    perPage?: number;
    sort?: string;
    status?: string;
    minPrice?: number;
    maxPrice?: number;
    category?: number[];
    seller?: number;
    brand?: number;
    inStock?: boolean;
    search?: string;
  }): Promise<ProductEntity[]> {
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.repository
      .createQueryBuilder("products")
      .where("1 = 1") // 조건 기본값
      .andWhere(status ? "products.status = :status" : "1=1", { status })
      // .andWhere(minPrice ? "products.price >= :minPrice" : "1=1", { minPrice })
      // .andWhere(maxPrice ? "products.price <= :maxPrice" : "1=1", { maxPrice })
      // .andWhere(category ? "products.categoryId = :category" : "1=1", { category })
      // .andWhere(seller ? "products.sellerId = :seller" : "1=1", { seller })
      // .andWhere(brand ? "products.brandId = :brand" : "1=1", { brand })
      // .andWhere(typeof inStock === "boolean" ? "products.stock > 0" : "1=1")
      .andWhere(search ? "products.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`products.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * perPage)
      .take(perPage);

    return query.getMany();
  }

  async findById(id: number): Promise<ProductEntity | null> {
    return await this.repository.findOne({ where: { id } });
  }

  async update(id: number, data: Partial<ProductEntity>): Promise<ProductEntity> {
    await this.repository.update(id, data);

    const updatedProduct = await this.findById(id);
    if (!updatedProduct) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updatedProduct;
  }

  async delete(id: number): Promise<void> {
    await this.repository.delete(id);
  }
}
