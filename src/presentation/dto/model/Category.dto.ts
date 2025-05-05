import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsInt, IsOptional, IsString, IsUrl, ValidateNested } from "class-validator";

class ParentCategoryDTO {
  @ApiProperty({ description: "카테고리 ID", example: 2 })
  @IsInt()
  id?: number;

  @ApiProperty({ description: "카테고리 이름", example: "거실 가구" })
  @IsString()
  name?: string;

  @ApiProperty({ description: "카테고리 슬러그", example: "living-room" })
  @IsString()
  slug?: string;
}

export default class CategoryDTO {
  @ApiProperty({ description: "카테고리 ID", example: 5 })
  @IsInt()
  id?: number;

  @ApiProperty({ description: "카테고리 이름", example: "소파" })
  @IsString()
  name?: string;

  @ApiProperty({ description: "카테고리 슬러그", example: "sofa" })
  @IsString()
  slug?: string;

  @ApiProperty({ description: "대표 카테고리 여부", example: true })
  @IsBoolean()
  is_primary: boolean;

  @ApiProperty({ description: "카테고리 설명", example: "다양한 스타일의 소파" })
  @IsString()
  description?: string;

  @ApiProperty({ description: "카테고리 레벨", example: 3 })
  @IsInt()
  level?: number;

  @ApiProperty({
    description: "카테고리 이미지 URL",
    example: "https://example.com/categories/sofa.jpg",
  })
  @IsUrl()
  image_url?: string;

  @ApiPropertyOptional({
    description: "부모 카테고리",
    type: ParentCategoryDTO,
    nullable: true,
  })
  @IsOptional()
  @ValidateNested()
  @Type(() => ParentCategoryDTO)
  parent?: ParentCategoryDTO | null;
}
