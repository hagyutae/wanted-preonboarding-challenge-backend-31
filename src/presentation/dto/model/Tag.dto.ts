import { ApiProperty } from "@nestjs/swagger";
import { IsInt, IsString } from "class-validator";

export default class TagDTO {
  @ApiProperty({ description: "태그 ID", example: 1 })
  @IsInt()
  public id?: number;

  @ApiProperty({ description: "태그 이름", example: "편안함" })
  @IsString()
  public name: string;

  @ApiProperty({ description: "슬러그", example: "comfort" })
  @IsString()
  public slug: string;
}
