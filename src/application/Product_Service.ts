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

  addOption(option: any) {
    // Logic to add an option to a product
  }

  updateOption(id: string, option: any) {
    // Logic to update an option of a product
  }

  deleteOption(id: string) {
    // Logic to delete an option from a product
  }

  addImage(image: any) {
    // Logic to add an image to a product
  }
}
