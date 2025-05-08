import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsInt, IsOptional, IsString, Min } from "class-validator";

export default class ProductOptionDTO {
  @ApiPropertyOptional({ description: "옵션 ID", example: 1 })
  @IsInt()
  @IsOptional()
  id?: number;

  @ApiProperty({ description: "옵션 그룹 ID", example: 35 })
  @IsOptional()
  @IsInt()
  option_group_id?: number;

  @ApiProperty({ description: "옵션 이름", example: "브라운" })
  @IsString()
  name: string;

  @ApiProperty({ description: "추가 가격", example: 25000 })
  @IsInt()
  @Min(0)
  additional_price: number;

  @ApiProperty({ description: "SKU", example: "SOFA-BRN" })
  @IsString()
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
