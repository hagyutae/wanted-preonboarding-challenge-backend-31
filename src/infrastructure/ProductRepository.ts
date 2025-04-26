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

  async findAll(): Promise<ProductEntity[]> {
    return await this.repository.find();
  }

  async findById(id: string): Promise<ProductEntity | null> {
    return await this.repository.findOne({ where: { id } });
  }

  async update(
    id: string,
    data: Partial<ProductEntity>,
  ): Promise<ProductEntity> {
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
