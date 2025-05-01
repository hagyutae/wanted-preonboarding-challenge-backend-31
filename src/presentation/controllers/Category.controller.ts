import { Controller, Get, Param, Query } from "@nestjs/common";
import { ApiBearerAuth, ApiOperation, ApiTags } from "@nestjs/swagger";

import { CategoryService } from "src/application/services";
import { Category, Product_Summary } from "src/domain/entities";
import { ApiBadRequestResponse, ApiErrorResponse, ApiStandardResponse } from "../decorators";
import {
  FiltersByCategoryDTO,
  NestedCategoryDTO,
  PaginationSummaryDTO,
  ParamDTO,
  ResponseDTO,
} from "../dto";
import { to_FilterDTO } from "../mappers";

@ApiTags("카테고리")
@ApiBearerAuth()
@Controller("categories")
@ApiErrorResponse()
export default class CategoryController {
  constructor(private readonly service: CategoryService) {}

  @ApiOperation({ summary: "카테고리 목록 조회" })
  @ApiStandardResponse("카테고리 목록을 성공적으로 조회했습니다.")
  @ApiBadRequestResponse("카테고리 목록 조회에 실패했습니다.")
  @Get()
  async read_categories(
    @Query() { level }: { level: number },
  ): Promise<ResponseDTO<NestedCategoryDTO[]>> {
    const data = await this.service.find_all_as_tree(level);

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
  async read_products(
    @Param() { id }: ParamDTO,
    @Query() query: FiltersByCategoryDTO,
  ): Promise<
    ResponseDTO<{
      category: Category;
      items: Product_Summary[];
      pagination: PaginationSummaryDTO;
    }>
  > {
    const data = await this.service.find_products_by_category_id(id, to_FilterDTO(query));

    return {
      success: true,
      data,
      message: "카테고리 상품 목록을 성공적으로 조회했습니다.",
    };
  }
}
