import { Body, Controller, Delete, Param, Post, Put } from "@nestjs/common";
import { ApiOperation, ApiParam, ApiTags } from "@nestjs/swagger";

import { ProductOptionsService } from "src/application/services";
import { ImageBodyDTO, OptionBodyDTO, OptionParamDTO, ParamDTO, ResponseDTO } from "../dto";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiStandardResponse,
} from "../decorators";

@ApiTags("상품 옵션 관리")
@Controller("products")
@ApiErrorResponse()
export default class ProductOptionsController {
  constructor(private readonly service: ProductOptionsService) {}

  @ApiOperation({ summary: "상품 옵션 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 옵션이 성공적으로 추가되었습니다.")
  @ApiBadRequestResponse("상품 옵션 추가에 실패했습니다.")
  @Post(":id/options")
  async create_option(
    @Param() { id }: ParamDTO,
    @Body() { option_group_id, ...body }: OptionBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.register(id, option_group_id!, body);

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
  @ApiBadRequestResponse("상품 옵션 수정에 실패했습니다.")
  @Put(":id/options/:option_id")
  async update_option(
    @Param() { id, option_id }: OptionParamDTO,
    @Body() body: OptionBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.update(id, option_id, body);

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
  @ApiBadRequestResponse("상품 옵션 삭제에 실패했습니다.")
  @Delete(":id/options/:option_id")
  async delete_option(@Param() { id, option_id }: OptionParamDTO): Promise<ResponseDTO> {
    await this.service.remove(id, option_id);

    return {
      success: true,
      data: null,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 이미지 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 이미지가 성공적으로 추가되었습니다.")
  @ApiBadRequestResponse("상품 이미지 추가에 실패했습니다.")
  @Post(":id/images")
  async create_image(
    @Param() { id }: OptionParamDTO,
    @Body() body: ImageBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.register_images(id, body.option_id, body);

    return {
      success: true,
      data,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    };
  }
}
