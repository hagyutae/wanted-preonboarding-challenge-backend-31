import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { MainService } from "src/application/services";
import { ResponseDTO } from "../dto";
import { ApiErrorResponse, ApiStandardResponse } from "../decorators";

@ApiTags("메인")
@Controller("main")
@ApiErrorResponse()
export default class MainController {
  constructor(private readonly service: MainService) {}

  @ApiOperation({ summary: "메인 페이지용 상품 목록" })
  @ApiStandardResponse("메인 페이지 상품 목록을 성공적으로 조회했습니다.")
  @Get()
  async getMainProducts(): Promise<ResponseDTO> {
    const new_products = await this.service.getNewProducts();

    const popular_products = await this.service.getPopularProducts();

    const featured_categories = await this.service.getFeaturedCategories();

    return {
      success: true,
      data: {
        new_products,
        popular_products,
        featured_categories,
      },
      message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
