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
import { ParamDTO, GetQueryDTO, ResponseDTO, PostBodyDTO } from "../dto";

@Controller("products")
export default class ProductController {
  constructor(private readonly productService: ProductService) {}

  @Post()
  async create(@Body() body: PostBodyDTO): Promise<ResponseDTO> {
    const data = await this.productService.create(body);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 등록되었습니다.",
    };
  }

  @Get(":id")
  async read(
    @Param() { id }: ParamDTO,
    @Query() query: GetQueryDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.getById(id);

    return {
      success: true,
      data,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    };
  }

  @Put(":id")
  async update(
    @Param() { id }: ParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.update(id, body);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 수정되었습니다.",
    };
  }

  @Delete(":id")
  async delete(@Param() { id }: ParamDTO): Promise<ResponseDTO> {
    const data = await this.productService.delete(id);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 삭제되었습니다.",
    };
  }
}
