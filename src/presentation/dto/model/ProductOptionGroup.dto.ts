import { ApiPropertyOptional, ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsInt, IsOptional, Min, IsArray, ValidateNested, IsString } from "class-validator";

import ProductOptionDTO from "./ProductOption.dto";

export default class ProductOptionGroupDTO {
  @ApiPropertyOptional({ description: "옵션 그룹 ID", example: 1 })
  @IsInt()
  @IsOptional()
  id?: number;

  @ApiProperty({ description: "옵션 그룹 이름", example: "색상" })
  @IsString()
  name: string;

  @ApiProperty({ description: "표시 순서", example: 1 })
  @IsInt()
  @Min(1)
  display_order: number;

  @ApiProperty({ description: "옵션 목록", type: [ProductOptionDTO] })
  @IsArray()
  @ValidateNested({ each: true })
  @Type(() => ProductOptionDTO)
  options?: ProductOptionDTO[];
}
