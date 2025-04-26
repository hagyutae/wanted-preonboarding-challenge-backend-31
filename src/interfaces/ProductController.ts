import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Put,
  Query,
} from "@nestjs/common";

import ProductService from "src/application/ProductService";
import { ParamDTO, GetQueryDTO, ResponseDTO, PostBodyDTO } from "./dto";

@Controller("products")
export default class ProductController {
  constructor(private readonly productService: ProductService) {}

  @Get(":id")
  async getProductById(
    @Param() { id }: ParamDTO,
    @Query() query: GetQueryDTO,
  ): Promise<ResponseDTO> {
    return this.productService.getById(id);
  }

  @Post()
  async createProduct(@Body() body: PostBodyDTO): Promise<ResponseDTO> {
    return this.productService.create(body);
  }

  @Put(":id")
  async updateProduct(
    @Param() { id }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    return this.productService.update(id, body);
  }

  @Delete(":id")
  async deleteProduct(@Param() { id }: ParamDTO): Promise<ResponseDTO> {
    return this.productService.delete(id);
  }
}
