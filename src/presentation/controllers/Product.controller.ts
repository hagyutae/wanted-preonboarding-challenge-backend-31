import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiOperation, ApiResponse, ApiTags } from "@nestjs/swagger";

import { ProductService } from "src/application/services";
import { BodyDTO, ProductParamDTO, ProductQueryDTO, ResponseDTO } from "../dto";

@ApiTags("상품 관리")
@Controller("products")
export default class ProductController {
  constructor(private readonly productService: ProductService) {}

  @ApiOperation({ summary: "상품 등록" })
  @ApiResponse({
    status: 201,
    description: "상품이 성공적으로 등록되었습니다.",
    type: ResponseDTO,
  })
  @Post()
  async create(@Body() body: BodyDTO): Promise<ResponseDTO> {
    const data = await this.productService.create(body);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 등록되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 목록 조회" })
  @ApiResponse({
    status: 200,
    description: "상품 목록을 성공적으로 조회했습니다.",
    type: ResponseDTO,
  })
  @Get()
  async readAll(@Query() query: ProductQueryDTO): Promise<ResponseDTO> {
    const data = await this.productService.getAll(query);

    return {
      success: true,
      data,
      message: "상품 목록을 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "상품 조회" })
  @ApiResponse({
    status: 200,
    description: "상품 상세 정보를 성공적으로 조회했습니다.",
    type: ResponseDTO,
  })
  @Get(":id")
  async read(@Param() { id }: ProductParamDTO): Promise<ResponseDTO> {
    const data = await this.productService.getById(id);

    return {
      success: true,
      data,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "상품 수정" })
  @ApiResponse({
    status: 200,
    description: "상품이 성공적으로 수정되었습니다.",
    type: ResponseDTO,
  })
  @Put(":id")
  async update(@Param() { id }: ProductParamDTO, @Body() body: BodyDTO): Promise<ResponseDTO> {
    const data = await this.productService.update(id, body);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 삭제" })
  @ApiResponse({
    status: 200,
    description: "상품이 성공적으로 삭제되었습니다.",
    type: ResponseDTO,
  })
  @Delete(":id")
  async delete(@Param() { id }: ProductParamDTO): Promise<ResponseDTO> {
    const data = await this.productService.delete(id);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 삭제되었습니다.",
    };
  }
}
