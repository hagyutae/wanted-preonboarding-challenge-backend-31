import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { MainService } from "src/application/services";
import { Category, Product_Summary } from "src/domain/entities";
import { ApiErrorResponse, ApiStandardResponse } from "../decorators";
import { ResponseDTO } from "../dto";

@ApiTags("메인")
@Controller("main")
@ApiErrorResponse()
export default class MainController {
  constructor(private readonly service: MainService) {}

  @ApiOperation({ summary: "메인 페이지용 상품 목록" })
  @ApiStandardResponse("메인 페이지 상품 목록을 성공적으로 조회했습니다.")
  @Get()
  async read_main_products(): Promise<
    ResponseDTO<{
      new_products: Product_Summary[];
      popular_products: Product_Summary[];
      featured_categories: Category[];
    }>
  > {
    const data = await this.service.find();

    return {
      success: true,
      data,
      message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
