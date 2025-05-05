import { ApiProperty } from "@nestjs/swagger";
import { IsInt, IsUrl } from "class-validator";

export default class BrandDTO {
  @ApiProperty({ description: "브랜드 ID", example: 1 })
  @IsInt()
  public id: number;

  @ApiProperty({ description: "브랜드 이름", example: "편안가구" })
  public name: string;

  @ApiProperty({ description: "브랜드 설명", example: "편안함을 추구하는 가구 브랜드" })
  public description: string;

  @ApiProperty({
    description: "브랜드 로고 URL",
    example: "https://example.com/brands/comfortfurniture.png",
  })
  @IsUrl()
  public logo_url: string;

  @ApiProperty({ description: "브랜드 웹사이트", example: "https://comfortfurniture.com" })
  @IsUrl()
  public website: string;
}
