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

  @Post(":id/options")
  async addOptions(
    @Param() { id }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    return this.productService.addOptions(id, body);
  }

  @Put(":id/options/:optionId")
  async updateOptions(
    @Param() { id, optionId }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    return this.productService.updateOptions(id, optionId!, body);
  }

  @Delete(":id/options/:optionId")
  async deleteOptions(
    @Param() { id, optionId }: ParamDTO,
  ): Promise<ResponseDTO> {
    return this.productService.deleteOptions(id, optionId!);
  }

  @Post(":id/images")
  async addImages(
    @Param() { id }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    return this.productService.addImages(id, body);
  }
}
