import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiBearerAuth, ApiOperation, ApiTags } from "@nestjs/swagger";

import { ProductService } from "src/product/application/services";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiStandardResponse,
  ResponseType,
} from "../decorators";
import {
  ParamDTO,
  ProductBodyDTO,
  ProductCatalogDTO,
  ProductQueryDTO,
  ProductResponseBundle,
  ProductResponseDTO,
  ResponseDTO,
} from "../dto";
import { to_FilterDTO } from "../mappers";

@ApiTags("상품 관리")
@ApiBearerAuth()
@Controller("products")
@ApiErrorResponse()
export default class ProductController {
  constructor(private readonly service: ProductService) {}

  @ApiOperation({ summary: "상품 등록" })
  @ApiCreatedResponse("상품이 성공적으로 등록되었습니다.", ProductResponseDTO)
  @ApiBadRequestResponse("상품 등록에 실패했습니다.")
  @Post()
  @ResponseType(ResponseDTO<ProductResponseDTO>)
  async create(@Body() body: ProductBodyDTO) {
    const data = await this.service.register(body);

    return {
      success: true,
      data,
      message: "상품이 성공적으로 등록되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 목록 조회" })
  @ApiStandardResponse("상품 목록을 성공적으로 조회했습니다.", ProductResponseBundle)
  @ApiBadRequestResponse("상품 목록 조회에 실패했습니다.")
  @Get()
  @ResponseType(ResponseDTO<ProductResponseBundle>)
  async read_all(@Query() query: ProductQueryDTO) {
    const data = await this.service.find_all(to_FilterDTO(query));

    return {
      success: true,
      data,
      message: "상품 목록을 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "상품 상세 조회" })
  @ApiStandardResponse("상품 상세 정보를 성공적으로 조회했습니다.", ProductCatalogDTO)
  @ApiBadRequestResponse("요청한 상품을 찾을 수 없습니다.")
  @Get(":id")
  @ResponseType(ResponseDTO<ProductCatalogDTO>)
  async read(@Param() { id }: ParamDTO) {
    const data = await this.service.find(id);

    return {
      success: true,
      data,
      message: "상품 상세 정보를 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "상품 수정" })
  @ApiStandardResponse("상품이 성공적으로 수정되었습니다.", ProductResponseDTO)
  @ApiBadRequestResponse("상품 수정에 실패했습니다.")
  @Put(":id")
  @ResponseType(ResponseDTO<ProductResponseDTO>)
  async update(@Param() { id }: ParamDTO, @Body() body: ProductBodyDTO) {
    const data = await this.service.edit(id, body);

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
  async delete(@Param() { id }: ParamDTO) {
    await this.service.remove(id);

    return {
      success: true,
      data: null,
      message: "상품이 성공적으로 삭제되었습니다.",
    };
  }
}
