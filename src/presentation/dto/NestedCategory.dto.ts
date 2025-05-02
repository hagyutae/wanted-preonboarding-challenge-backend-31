import { ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsArray, IsInt, IsOptional, IsUrl, ValidateNested } from "class-validator";

export default class NestedCategoryDTO {
  @ApiPropertyOptional({ description: "카테고리 ID" })
  @IsOptional()
  @IsInt()
  id: number;

  @ApiPropertyOptional({ description: "카테고리 이름" })
  @IsOptional()
  name: string;

  @ApiPropertyOptional({ description: "카테고리 슬러그" })
  @IsOptional()
  slug: string;

  @ApiPropertyOptional({ description: "카테고리 설명" })
  @IsOptional()
  description: string;

  @ApiPropertyOptional({ description: "카테고리 레벨" })
  @IsOptional()
  @IsInt()
  level: number;

  @ApiPropertyOptional({ description: "카테고리 이미지 URL" })
  @IsOptional()
  @IsUrl({}, { message: "유효한 URL 형식이 아닙니다." })
  image_url: string;

  @ApiPropertyOptional({ type: () => [NestedCategoryDTO], description: "중첩 카테고리 목록" })
  @IsOptional()
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => NestedCategoryDTO)
  children?: NestedCategoryDTO[];
}
