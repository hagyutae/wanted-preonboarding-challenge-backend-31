import { ApiProperty } from "@nestjs/swagger";

export class ParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  id: string;
}

export class OptionParamDTO {
  @ApiProperty({ description: "고유 식별자", example: "123" })
  id: string;

  @ApiProperty({ description: "옵션에 대한 식별자", example: "456" })
  optionId: string;
}
