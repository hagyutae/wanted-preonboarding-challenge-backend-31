import { Controller, Get, Param, Query } from "@nestjs/common";
import { ApiOperation, ApiTags } from "@nestjs/swagger";

import { CategoryService } from "src/application/services";
import { FiltersByCategoryDTO, ResponseDTO } from "../dto";
import { ApiStandardResponse, ApiErrorResponse, ApiBadRequestResponse } from "../decorators";

@ApiTags("카테고리")
@Controller("categories")
@ApiErrorResponse()
export default class CategoryController {
  constructor(private readonly categoryService: CategoryService) {}

  @ApiOperation({ summary: "카테고리 목록 조회" })
  @ApiStandardResponse("카테고리 목록을 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("카테고리 목록 조회에 실패했습니다.")
  @Get()
  async readCategories(@Query() { level }: { level: number }): Promise<ResponseDTO> {
    const data = await this.categoryService.getAllCategoriesAsTree(level);

    return {
      success: true,
      data,
      message: "카테고리 목록을 성공적으로 조회했습니다.",
    };
  }

  @ApiOperation({ summary: "특정 카테고리의 상품 목록 조회" })
  @ApiStandardResponse("특정 카테고리의 상품 목록을 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("특정 카테고리의 상품 목록 조회에 실패했습니다.")
  @Get(":id/products")
  async readProducts(
    @Param() { id }: { id: number },
    @Query() query: FiltersByCategoryDTO,
  ): Promise<ResponseDTO> {
    const data = await this.categoryService.getProductsByCategoryId(id, query);

    return {
      success: true,
      data,
      message: "카테고리 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
