import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { ReviewService } from "src/application/services";
import { ProductParamDTO, ResponseDTO, ReviewBodyDTO, ReviewQueryDTO } from "../dto";

@ApiTags("리뷰")
@Controller("reviews")
export default class ReviewController {
  constructor(private readonly reviewService: ReviewService) {}

  @ApiOperation({ summary: "상품 리뷰 조회" })
  @Get(":id")
  async read(
    @Param() { id }: ProductParamDTO,
    @Query() query: ReviewQueryDTO,
  ): Promise<ResponseDTO> {
    const data = await this.reviewService.get(id, query);

    return {
      success: true,
      data,
      message: "상품 리뷰를 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 작성" })
  @Post(":id")
  async create(
    @Param() { id }: ProductParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.reviewService.create(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 작성되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 수정" })
  @Put(":id")
  async update(
    @Param() { id }: ProductParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO> {
    const data = await this.reviewService.update(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 삭제" })
  @Delete(":id")
  async delete(@Param() { id }: ProductParamDTO): Promise<ResponseDTO> {
    await this.reviewService.delete(id);

    return {
      success: true,
      data: null,
      message: "리뷰가 성공적으로 삭제되었습니다.",
    };
  }
}
