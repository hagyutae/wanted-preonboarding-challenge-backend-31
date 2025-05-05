import { ApiProperty, ApiPropertyOptional, PickType } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsInt, IsOptional, ValidateNested } from "class-validator";

export default class CategoryDTO {
  @ApiProperty({ description: "카테고리 ID", example: 5 })
  @IsInt()
  id?: number;

  @ApiProperty({ description: "카테고리 이름", example: "소파" })
  name?: string;

  @ApiProperty({ description: "카테고리 슬러그", example: "sofa" })
  slug?: string;

  @ApiProperty({ description: "대표 카테고리 여부", example: true })
  is_primary: boolean;

  @ApiPropertyOptional({
    description: "부모 카테고리",
    example: {
      id: 2,
      name: "거실 가구",
      slug: "living-room",
    },
    nullable: true,
  })
  @IsOptional()
  @ValidateNested()
  @Type(() => ParentCategoryDTO)
  parent?: () => ParentCategoryDTO;
}

export class ParentCategoryDTO extends PickType(CategoryDTO, ["id", "name", "slug"] as const) {}
