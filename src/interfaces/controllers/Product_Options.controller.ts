import { Body, Controller, Delete, Param, Post, Put } from "@nestjs/common";

import ProductService from "src/application/ProductService";
import { OptionParamDTO, PostBodyDTO, ResponseDTO } from "../dto";

@Controller("products")
export default class ProductOptionsController {
  constructor(private readonly productService: ProductService) {}

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

  @Delete(":id/options/:optionId")
  async deleteOptions(
    @Param() { id, optionId }: OptionParamDTO,
  ): Promise<ResponseDTO> {
    const data = await this.productService.deleteOptions(id, optionId);

    return {
      success: true,
      data,
      message: "상품 옵션이 성공적으로 삭제되었습니다.",
    };
  }

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
