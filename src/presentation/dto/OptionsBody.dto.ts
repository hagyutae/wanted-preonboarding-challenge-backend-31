import { ApiProperty } from "@nestjs/swagger";
import { IsInt, IsOptional, Min } from "class-validator";

export default class OptionsBodyDTO {
  @ApiProperty({ description: "옵션 그룹 ID", example: 35, required: false })
  @IsOptional()
  @IsInt()
  public option_group_id?: number;

  @ApiProperty({ description: "옵션 이름", example: "네이비 블루" })
  public name: string;

  @ApiProperty({ description: "추가 가격", example: 25000 })
  @IsInt()
  @Min(0)
  public additional_price: number;

  @ApiProperty({ description: "SKU(재고 관리 코드)", example: "SOFA-NVBL" })
  public sku: string;

  @ApiProperty({ description: "재고 수량", example: 10 })
  @IsInt()
  @Min(1)
  public stock: number;

  @ApiProperty({ description: "표시 순서", example: 3 })
  @IsInt()
  @Min(1)
  public display_order: number;
}
