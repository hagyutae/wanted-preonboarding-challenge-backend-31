import { Controller, Get } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";
import { plainToInstance } from "class-transformer";

import { MainService } from "src/application/services";
import { ApiErrorResponse, ApiStandardResponse } from "../decorators";
import { MainResponseBundle, ResponseDTO } from "../dto";

@ApiTags("메인")
@Controller("main")
@ApiErrorResponse()
export default class MainController {
  constructor(private readonly service: MainService) {}

  @ApiOperation({ summary: "메인 페이지용 상품 목록" })
  @ApiStandardResponse("메인 페이지 상품 목록을 성공적으로 조회했습니다.", MainResponseBundle)
  @Get()
  async read_main_products(): Promise<ResponseDTO<MainResponseBundle>> {
    const plain = await this.service.find();

    const data = plainToInstance(MainResponseBundle, plain, {
      enableImplicitConversion: true,
    });

    return {
      success: true,
      data,
      message: "메인 페이지 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
