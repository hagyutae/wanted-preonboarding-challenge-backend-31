import { ApiProperty, getSchemaPath } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsInt, IsOptional, IsUrl, ValidateNested } from "class-validator";

export default class NestedCategoryDTO {
  @ApiProperty({ description: "카테고리 ID" })
  @IsInt()
  id: number;

  @ApiProperty({ description: "카테고리 이름" })
  name: string;

  @ApiProperty({ description: "카테고리 슬러그" })
  slug: string;

  @ApiProperty({ description: "카테고리 설명" })
  description: string;

  @ApiProperty({ description: "카테고리 레벨" })
  @IsInt()
  level: number;

  @ApiProperty({ description: "카테고리 이미지 URL" })
  @IsUrl({}, { message: "유효한 URL 형식이 아닙니다." })
  image_url: string;

  @ApiProperty({
    type: "array",
    items: { $ref: getSchemaPath(NestedCategoryDTO) },
    description: "중첩 카테고리 목록",
  })
  @IsOptional()
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => NestedCategoryDTO)
  children?: NestedCategoryDTO[];
}
