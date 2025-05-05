import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import { IsEmail, IsInt, IsNumber, IsOptional, IsPhoneNumber, IsUrl, Min } from "class-validator";

export default class SellerDTO {
  @ApiPropertyOptional({ description: "판매자 ID", example: 1 })
  @IsOptional()
  @IsInt()
  public id?: number;

  @ApiProperty({ description: "판매자 이름", example: "홈퍼니처" })
  public name: string;

  @ApiProperty({ description: "판매자 설명", example: "최고의 가구 전문 판매점" })
  public description: string;

  @ApiProperty({
    description: "판매자 로고 URL",
    example: "https://example.com/sellers/homefurniture.png",
  })
  @IsUrl()
  public logo_url: string;

  @ApiProperty({ description: "판매자 평점", example: 4.8 })
  @IsNumber()
  @Min(0)
  public rating: number;

  @ApiProperty({ description: "판매자 연락 이메일", example: "contact@homefurniture.com" })
  @IsEmail()
  public contact_email: string;

  @ApiProperty({ description: "판매자 연락 전화번호", example: "02-1234-5678" })
  @IsPhoneNumber()
  public contact_phone: string;
}
