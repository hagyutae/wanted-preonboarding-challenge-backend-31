import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsInt, Min, IsArray, ValidateNested, IsOptional } from "class-validator";

export class ProductOptionDTO {
  @ApiPropertyOptional({ description: "옵션 ID", example: 1 })
  @IsInt()
  @IsOptional()
  id?: number;

  @ApiProperty({ description: "옵션 이름", example: "브라운" })
  name: string;

  @ApiProperty({ description: "추가 가격", example: 0 })
  @IsInt()
  @Min(0)
  additional_price: number;

  @ApiProperty({ description: "SKU", example: "SOFA-BRN" })
  sku: string;

  @ApiProperty({ description: "재고", example: 10 })
  @IsInt()
  @Min(0)
  stock: number;

  @ApiProperty({ description: "표시 순서", example: 1 })
  @IsInt()
  @Min(1)
  display_order: number;
}

export default class ProductOptionGroupDTO {
  @ApiPropertyOptional({ description: "옵션 그룹 ID", example: 1 })
  @IsInt()
  @IsOptional()
  id?: number;

  @ApiProperty({ description: "옵션 그룹 이름", example: "색상" })
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
