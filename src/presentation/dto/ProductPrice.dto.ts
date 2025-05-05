import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsInt, IsNumber, IsOptional, Matches, Max, Min } from "class-validator";

export default class ProductPriceDTO {
  @ApiProperty({ description: "기본 가격", example: 599000 })
  @IsInt()
  @Min(0)
  base_price: number;

  @ApiProperty({ description: "할인 가격", example: 499000 })
  @IsInt()
  @Min(0)
  sale_price: number;

  @ApiPropertyOptional({ description: "원가", example: 350000 })
  @IsOptional()
  @IsInt()
  @Min(0)
  cost_price: number;

  @ApiProperty({ description: "통화", example: "KRW" })
  @Matches(/^[A-Z]{3}$/, { message: "통화 코드는 3자리 대문자여야 합니다 (예: USD, KRW)" })
  currency: string;

  @ApiProperty({ description: "세율", example: 10 })
  @IsNumber()
  @Min(0)
  tax_rate: number;

  @ApiPropertyOptional({ description: "할인율", example: 17 })
  @IsOptional()
  @IsNumber()
  @Min(0)
  @Max(100)
  discount_percentage: number;
}
