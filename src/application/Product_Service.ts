import IRepository from "src/infrastructure/IRepository";

export default class Product_Service {
  constructor(private repository: IRepository<any>) {}

  async create(data: any) {
    await this.repository.create(data);
  }

  async getAll() {
    await this.repository.findAll();
  }

  async getById(id: string) {
    await this.repository.findById(id);
  }

  async update(id: string, data: any) {
    await this.repository.update(id, data);
  }

  async delete(id: string) {
    await this.repository.delete(id);
  }

  async addOption(id: string, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: [...(product?.options_group || []), option],
    };

    await this.repository.update(id, updatedProduct);
  }

  async updateOption(id: string, option: any) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.options_group.map((opt: any) =>
        opt.id === option.id ? option : opt,
      ),
    };

    await this.repository.update(id, updatedProduct);
  }

  async deleteOption(id: string) {
    const product = await this.repository.findById(id);

    const updatedProduct = {
      ...product,
      option_groups: product?.options_group.filter((opt: any) => opt.id !== id),
    };

    await this.repository.update(id, updatedProduct);
  }

  addImage(image: any) {
    // Logic to add an image to a product
  }
}
