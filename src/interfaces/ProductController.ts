import { Controller, Delete, Get, Post, Put } from "@nestjs/common";

import Product from "src/domain/Product";
import Product_Service from "src/application/Product_Service";
import ResponseDTO from "./ResponseDTO";

@Controller("products")
export default class ProductController {
  constructor(private readonly productService: Product_Service) {}

  @Get(":id")
  async getProductById(id: string): Promise<ResponseDTO> {
    return this.productService.getById(id);
  }

  @Post()
  async createProduct(productData: Partial<Product>): Promise<ResponseDTO> {
    return this.productService.create(productData);
  }

  @Put(":id")
  async updateProduct(
    id: string,
    productData: Partial<Product>,
  ): Promise<ResponseDTO> {
    return this.productService.update(id, productData);
  }

  @Delete(":id")
  async deleteProduct(id: string): Promise<ResponseDTO> {
    return this.productService.delete(id);
  }
}
