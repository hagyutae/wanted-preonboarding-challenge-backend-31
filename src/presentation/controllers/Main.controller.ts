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
  async read_main_products(): Promise<ResponseDTO> {
    const data = await this.service.find();

    return {
      success: true,
      data,
      message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
