import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiBearerAuth, ApiOperation, ApiTags } from "@nestjs/swagger";
import { plainToInstance } from "class-transformer";

import { ProductService } from "src/application/services";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiStandardResponse,
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
  async create(@Body() body: ProductBodyDTO): Promise<ResponseDTO<ProductResponseDTO>> {
    const plain = await this.service.register(body);

    const data = plainToInstance(ProductResponseDTO, plain, {
      enableImplicitConversion: true,
    });

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
  async read_all(@Query() query: ProductQueryDTO): Promise<ResponseDTO<ProductResponseBundle>> {
    const plain = await this.service.find_all(to_FilterDTO(query));

    const data = plainToInstance(ProductResponseBundle, plain, {
      enableImplicitConversion: true,
    });

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
  async read(@Param() { id }: ParamDTO): Promise<ResponseDTO<ProductCatalogDTO>> {
    const plain = await this.service.find(id);

    const data = plainToInstance(ProductCatalogDTO, plain, {
      enableImplicitConversion: true,
    });

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
  async update(
    @Param() { id }: ParamDTO,
    @Body() body: ProductBodyDTO,
  ): Promise<ResponseDTO<ProductResponseDTO>> {
    const plain = await this.service.edit(id, body);

    const data = plainToInstance(ProductResponseDTO, plain, {
      enableImplicitConversion: true,
    });

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
  async delete(@Param() { id }: ParamDTO): Promise<ResponseDTO<null>> {
    await this.service.remove(id);

    return {
      success: true,
      data: null,
      message: "상품이 성공적으로 삭제되었습니다.",
    };
  }
}
