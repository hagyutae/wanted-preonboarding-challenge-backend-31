import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { ReviewService } from "src/application/services";
import { ProductParamDTO, ResponseDTO, ReviewBodyDTO, ReviewQueryDTO } from "../dto";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiForbiddenResponse,
  ApiStandardResponse,
} from "../decorators";

@ApiTags("리뷰")
@Controller("reviews")
@ApiErrorResponse()
export default class ReviewController {
  constructor(private readonly service: ReviewService) {}

  @ApiOperation({ summary: "상품 리뷰 조회" })
  @ApiStandardResponse("상품 리뷰를 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("상품 리뷰 조회에 실패했습니다.")
  @Get(":id")
  async read(
    @Param() { id }: ProductParamDTO,
    @Query() query: ReviewQueryDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.get(id, query);

    return {
      success: true,
      data,
      message: "상품 리뷰를 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 작성" })
  @ApiCreatedResponse("리뷰가 성공적으로 작성되었습니다.")
  @ApiBadRequestResponse("리뷰 작성에 실패했습니다.")
  @Post(":id")
  async create(
    @Param() { id }: ProductParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.create(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 작성되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 수정" })
  @ApiStandardResponse("리뷰가 성공적으로 수정되었습니다.")
  @ApiForbiddenResponse("다른 사용자의 리뷰를 수정할 권한이 없습니다.")
  @Put(":id")
  async update(
    @Param() { id }: ProductParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.service.update(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 삭제" })
  @ApiStandardResponse("리뷰가 성공적으로 삭제되었습니다.")
  @ApiForbiddenResponse("다른 사용자의 리뷰를 삭제할 권한이 없습니다.")
  @Delete(":id")
  async delete(@Param() { id }: ProductParamDTO): Promise<ResponseDTO> {
    await this.service.delete(id);

    return {
      success: true,
      data: null,
      message: "리뷰가 성공적으로 삭제되었습니다.",
    };
  }
}
