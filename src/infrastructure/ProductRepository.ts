import { Repository } from "typeorm";
import { InjectRepository } from "@nestjs/typeorm";
import { Injectable } from "@nestjs/common";

import IRepository from "./IRepository";
import { Product } from "./entities/Product.entity";

@Injectable()
export default class ProductRepository implements IRepository<Product> {
  constructor(
    @InjectRepository(Product)
    private readonly repository: Repository<Product>,
  ) {}

  async create(data: Product): Promise<Product> {
    const entity = this.repository.create(data);
    return await this.repository.save(entity);
  }

  async findAll(): Promise<Product[]> {
    return await this.repository.find();
  }

  async findById(id: string): Promise<Product | null> {
    return await this.repository.findOne({ where: { id } });
  }

  async update(id: string, data: Partial<Product>): Promise<Product> {
    await this.repository.update(id, data);

    const updatedProduct = await this.findById(id);
    if (!updatedProduct) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updatedProduct;
  }

  async delete(id: string): Promise<void> {
    await this.repository.delete(id);
  }
}
