import { Body, Controller, Delete, Param, Post, Put } from "@nestjs/common";
import { ApiBearerAuth, ApiOperation, ApiParam, ApiTags } from "@nestjs/swagger";

import { ProductOptionsService } from "src/application/services";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiStandardResponse,
  ResponseType,
} from "../decorators";
import { ImageDTO, OptionParamDTO, ParamDTO, ProductOptionDTO, ResponseDTO } from "../dto";

@ApiTags("상품 옵션 관리")
@ApiBearerAuth()
@Controller("products")
@ApiErrorResponse()
export default class ProductOptionsController {
  constructor(private readonly service: ProductOptionsService) {}

  @ApiOperation({ summary: "상품 옵션 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 옵션이 성공적으로 추가되었습니다.", ProductOptionDTO)
  @ApiBadRequestResponse("상품 옵션 추가에 실패했습니다.")
  @Post(":id/options")
  @ResponseType(ResponseDTO<ProductOptionDTO>)
  async create_option(
    @Param() { id }: ParamDTO,
    @Body() { option_group_id, ...body }: ProductOptionDTO,
  ) {
    const data = await this.service.register(id, option_group_id!, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 추가되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 수정" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "optionId", description: "옵션 ID" })
  @ApiStandardResponse("상품 옵션이 성공적으로 수정되었습니다.", ProductOptionDTO)
  @ApiBadRequestResponse("상품 옵션 수정에 실패했습니다.")
  @Put(":id/options/:optionId")
  @ResponseType(ResponseDTO<ProductOptionDTO>)
  async update_option(@Param() { id, optionId }: OptionParamDTO, @Body() body: ProductOptionDTO) {
    const data = await this.service.update(id, optionId, body);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 옵션 삭제" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiParam({ name: "optionId", description: "옵션 ID" })
  @ApiStandardResponse("상품 옵션이 성공적으로 삭제되었습니다.")
  @ApiBadRequestResponse("상품 옵션 삭제에 실패했습니다.")
  @Delete(":id/options/:optionId")
  async delete_option(@Param() { id, optionId }: OptionParamDTO) {
    await this.service.remove(id, optionId);

    return {
      success: true,
      data: null,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    };
  }

  @ApiOperation({ summary: "상품 이미지 추가" })
  @ApiParam({ name: "id", description: "상품 ID" })
  @ApiCreatedResponse("상품 이미지가 성공적으로 추가되었습니다.", ImageDTO)
  @ApiBadRequestResponse("상품 이미지 추가에 실패했습니다.")
  @Post(":id/images")
  @ResponseType(ResponseDTO<ImageDTO>)
  async create_image(@Param() { id }: OptionParamDTO, @Body() body: ImageDTO) {
    const data = await this.service.register_images(id, body.option_id!, body);

    return {
      success: true,
      data,
      message: "상품 이미지가 성공적으로 추가되었습니다.",
    };
  }
}
