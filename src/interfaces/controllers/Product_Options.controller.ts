import { Body, Controller, Delete, Param, Post, Put } from "@nestjs/common";
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from "@nestjs/swagger";

import ProductService from "src/application/ProductService";
import { OptionParamDTO, PostBodyDTO, ResponseDTO } from "../dto";

@ApiTags("상품 옵션 관리")
@Controller("products")
export default class ProductOptionsController {
  constructor(private readonly productService: ProductService) {}

  @ApiOperation({ summary: "상품 옵션 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiResponse({
    status: 201,
    description: "상품 옵션이 성공적으로 추가되었습니다.",
    type: ResponseDTO,
  })
  @Post(":id/options")
  async addOptions(
    @Param() { id }: OptionParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.addOptions(id, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 수정" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "optionId", description: "옵션 ID" })
  @ApiResponse({
    status: 200,
    description: "상품 옵션이 성공적으로 수정되었습니다.",
    type: ResponseDTO,
  })
  @Put(":id/options/:optionId")
  async updateOptions(
    @Param() { id, optionId }: OptionParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.updateOptions(id, optionId, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 삭제" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "optionId", description: "옵션 ID" })
  @ApiResponse({
    status: 200,
    description: "상품 옵션이 성공적으로 삭제되었습니다.",
    type: ResponseDTO,
  })
  @Delete(":id/options/:optionId")
  async deleteOptions(@Param() { id, optionId }: OptionParamDTO): Promise<ResponseDTO> {
    const data = await this.productService.deleteOptions(id, optionId);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 이미지 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiResponse({
    status: 201,
    description: "상품 이미지가 성공적으로 추가되었습니다.",
    type: ResponseDTO,
  })
  @Post(":id/images")
  async addImages(
    @Param() { id }: OptionParamDTO,
    @Body() body: PostBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.addImages(id, body);

    return {
      success: true,
      data,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    };
  }
}
