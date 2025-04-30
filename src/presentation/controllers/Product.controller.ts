import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { ProductService } from "src/application/services";
import { BodyDTO, ProductParamDTO, ProductQueryDTO, ResponseDTO } from "../dto";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiStandardResponse,
} from "../decorators";

@ApiTags("상품 관리")
@Controller("products")
@ApiErrorResponse()
export default class ProductController {
  constructor(private readonly productService: ProductService) {}

  @ApiOperation({ summary: "상품 등록" })
  @ApiCreatedResponse("상품이 성공적으로 등록되었습니다.")
  @ApiBadRequestResponse("상품 등록에 실패했습니다.")
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
  @ApiStandardResponse("상품 목록을 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("상품 목록 조회에 실패했습니다.")
  @Get()
  async readAll(@Query() query: ProductQueryDTO): Promise<ResponseDTO> {
    const data = await this.productService.getAll(query);

    return {
      success: true,
      data,
      message: "상품 목록을 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "상품 상세 조회" })
  @ApiStandardResponse("상품 상세 정보를 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("요청한 상품을 찾을 수 없습니다.")
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
  @ApiStandardResponse("상품이 성공적으로 수정되었습니다.")
  @ApiBadRequestResponse("상품 수정에 실패했습니다.")
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
  @ApiStandardResponse("상품이 성공적으로 삭제되었습니다.")
  @ApiBadRequestResponse("상품 삭제에 실패했습니다.")
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
