import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsInt } from "class-validator";

export class ParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  @Type(() => Number)
  @IsInt()
  id: number;
}

export class OptionParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  @Type(() => Number)
  @IsInt()
  id: number;

  @ApiProperty({ description: "옵션에 대한 식별자", example: "456" })
  @Type(() => Number)
  @IsInt()
  option_id: number;
}
