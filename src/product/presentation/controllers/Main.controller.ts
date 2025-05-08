import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { MainService } from "src/product/application/services";
import { ApiErrorResponse, ApiStandardResponse, ResponseType } from "../decorators";
import { MainResponseBundle, ResponseDTO } from "../dto";

@ApiTags("메인")
@Controller("main")
@ApiErrorResponse()
export default class MainController {
  constructor(private readonly service: MainService) {}

  @ApiOperation({ summary: "메인 페이지용 상품 목록" })
  @ApiStandardResponse("메인 페이지 상품 목록을 성공적으로 조회했습니다.", MainResponseBundle)
  @Get()
  @ResponseType(ResponseDTO<MainResponseBundle>)
  async read_main_products() {
    const data = await this.service.find();

    return {
      success: true,
      data,
      message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
