import { ApiPropertyOptional, getSchemaPath, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsOptional, ValidateNested } from "class-validator";

import CategoryDTO from "../model/Category.dto";

export default class NestedCategoryDTO extends PickType(CategoryDTO, [
  "id",
  "name",
  "slug",
  "description",
  "level",
  "image_url",
] as const) {
  @ApiPropertyOptional({
    description: "중첩 카테고리 목록",
    type: "array",
    items: { $ref: getSchemaPath(NestedCategoryDTO) },
  })
  @IsOptional()
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => NestedCategoryDTO)
  children?: NestedCategoryDTO[];
}
