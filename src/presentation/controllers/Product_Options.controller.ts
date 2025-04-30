import { Body, Controller, Delete, Param, Post, Put } from "@nestjs/common";
import { ApiOperation, ApiParam, ApiResponse, ApiTags } from "@nestjs/swagger";

import { ProductOptionsService } from "src/application/services";
import { ImageBodyDTO, OptionBodyDTO, OptionParamDTO, ProductParamDTO, ResponseDTO } from "../dto";
import { ApiCreatedResponse, ApiStandardResponse } from "../decorators";

@ApiTags("상품 옵션 관리")
@Controller("products")
export default class ProductOptionsController {
  constructor(private readonly productOptionsService: ProductOptionsService) {}

  @ApiOperation({ summary: "상품 옵션 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 옵션이 성공적으로 추가되었습니다.")
  @Post(":id/options")
  async addOptions(
    @Param() { id }: ProductParamDTO,
    @Body() body: OptionBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productOptionsService.addOptions(id, body.option_group_id!, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 수정" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "option_id", description: "옵션 ID" })
  @ApiStandardResponse("상품 옵션이 성공적으로 수정되었습니다.")
  @Put(":id/options/:option_id")
  async updateOptions(
    @Param() { id, option_id }: OptionParamDTO,
    @Body() body: OptionBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productOptionsService.updateOptions(id, option_id, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 삭제" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "option_id", description: "옵션 ID" })
  @ApiStandardResponse("상품 옵션이 성공적으로 삭제되었습니다.")
  @Delete(":id/options/:option_id")
  async deleteOptions(@Param() { id, option_id }: OptionParamDTO): Promise<ResponseDTO> {
    await this.productOptionsService.deleteOptions(id, option_id);

    return {
      success: true,
      data: null,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 이미지 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 이미지가 성공적으로 추가되었습니다.")
  @Post(":id/images")
  async addImages(
    @Param() { id }: OptionParamDTO,
    @Body() body: ImageBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productOptionsService.addImages(id, body.option_id, body);

    return {
      success: true,
      data,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    };
  }
}
