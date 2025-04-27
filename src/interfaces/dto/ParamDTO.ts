import { ApiProperty } from "@nestjs/swagger";

export class ProductParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  id: number;
}

export class OptionParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  id: number;

  @ApiProperty({ description: "옵션에 대한 식별자", example: "456" })
  option_id: number;
}
