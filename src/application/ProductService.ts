import { Inject, Injectable } from "@nestjs/common";

import IRepository from "src/infrastructure/IRepository";

@Injectable()
export default class ProductService {
  constructor(
    @Inject("IRepository")
    private repository: IRepository<any>,
  ) {}

  async create(data: any) {
    return this.repository.create(data);
  }

  async getAll(query: any) {
    return this.repository.findAll(query);
  }

  async getById(id: number) {
    return this.repository.findById(id);
  }

  async update(id: number, data: any) {
    return this.repository.update(id, data);
  }

  async delete(id: number) {
    await this.repository.delete(id);
  }

  async addOptions(id: number, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: [...(product?.option_groups || []), option],
    };

    await this.repository.update(id, updatedProduct);

    return updatedProduct;
  }

  async updateOptions(id: number, optionid: number, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.option_groups.map((opt: any) =>
        opt.id === option.id ? option : opt,
      ),
    };

    await this.repository.update(id, updatedProduct);

    return updatedProduct;
  }

  async deleteOptions(id: number, optionid: number) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.option_groups.filter((opt: any) => opt.id !== id),
    };

    return await this.repository.update(id, updatedProduct);
  }

  async addImages(id: number, body: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      images: [...(product?.images || []), body],
    };

    await this.repository.update(id, updatedProduct);

    return updatedProduct;
  }
}
