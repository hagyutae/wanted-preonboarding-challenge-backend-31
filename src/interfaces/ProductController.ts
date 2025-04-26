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

  @Post()
  async create(@Body() body: PostBodyDTO): Promise<ResponseDTO> {
    return this.productService.create(body);
  }

  @Get(":id")
  async read(
    @Param() { id }: ParamDTO,
    @Query() query: GetQueryDTO,
  ): Promise<ResponseDTO> {
    return this.productService.getById(id);
  }

  @Put(":id")
  async update(
    @Param() { id }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    return this.productService.update(id, body);
  }

  @Delete(":id")
  async delete(@Param() { id }: ParamDTO): Promise<ResponseDTO> {
    return this.productService.delete(id);
  }
}
