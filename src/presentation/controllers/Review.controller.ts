import { Body, Controller, Delete, Get, Param, Post, Put, Query } from "@nestjs/common";
import { ApiBearerAuth, ApiOperation, ApiTags } from "@nestjs/swagger";

import { ReviewService } from "src/application/services";
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiErrorResponse,
  ApiForbiddenResponse,
  ApiStandardResponse,
  ResponseType,
} from "../decorators";
import {
  ParamDTO,
  ResponseDTO,
  ReviewBodyDTO,
  ReviewDTO,
  ReviewQueryDTO,
  ReviewResponseBundle,
  ReviewResponseDTO,
} from "../dto";
import { to_FilterDTO } from "../mappers";

@ApiTags("리뷰")
@ApiBearerAuth()
@Controller("")
@ApiErrorResponse()
export default class ReviewController {
  constructor(private readonly service: ReviewService) {}

  @ApiOperation({ summary: "상품 리뷰 조회" })
  @ApiStandardResponse("상품 리뷰를 성공적으로 조회했습니다.", ReviewResponseBundle)
  @ApiBadRequestResponse("상품 리뷰 조회에 실패했습니다.")
  @Get("products/:id/reviews")
  @ResponseType(ResponseDTO<ReviewResponseBundle>)
  async read(
    @Param() { id }: ParamDTO,
    @Query() query: ReviewQueryDTO,
  ): Promise<ResponseDTO<ReviewResponseBundle>> {
    const data = await this.service.find(id, to_FilterDTO(query));

    return {
      success: true,
      data,
      message: "상품 리뷰를 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 작성" })
  @ApiCreatedResponse("리뷰가 성공적으로 작성되었습니다.", ReviewDTO)
  @ApiBadRequestResponse("리뷰 작성에 실패했습니다.")
  @Post("products/:id/reviews")
  @ResponseType(ResponseDTO<ReviewDTO>)
  async create(
    @Param() { id }: ParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO<ReviewDTO>> {
    const data = await this.service.register(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 작성되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 수정" })
  @ApiStandardResponse("리뷰가 성공적으로 수정되었습니다.", ReviewResponseDTO)
  @ApiForbiddenResponse("다른 사용자의 리뷰를 수정할 권한이 없습니다.")
  @Put("reviews/:id")
  @ResponseType(ResponseDTO<ReviewResponseDTO>)
  async update(
    @Param() { id }: ParamDTO,
    @Body() body: ReviewBodyDTO,
  ): Promise<ResponseDTO<ReviewResponseDTO>> {
    const data = await this.service.edit(id, body);

    return {
      success: true,
      data,
      message: "리뷰가 성공적으로 수정되었습니다.",
    };
  }

  @ApiOperation({ summary: "리뷰 삭제" })
  @ApiStandardResponse("리뷰가 성공적으로 삭제되었습니다.")
  @ApiForbiddenResponse("다른 사용자의 리뷰를 삭제할 권한이 없습니다.")
  @Delete("reviews/:id")
  async delete(@Param() { id }: ParamDTO) {
    await this.service.remove(id);

    return {
      success: true,
      data: null,
      message: "리뷰가 성공적으로 삭제되었습니다.",
    };
  }
}
