import { ApiProperty, ApiPropertyOptional } from "@nestjs/swagger";
import {
  IsEmail,
  IsInt,
  IsNumber,
  IsOptional,
  IsString,
  IsUrl,
  Matches,
  Min,
} from "class-validator";

export default class SellerDTO {
  @ApiPropertyOptional({ description: "판매자 ID", example: 1 })
  @IsOptional()
  @IsInt()
  id?: number;

  @ApiProperty({ description: "판매자 이름", example: "홈퍼니처" })
  @IsString()
  name: string;

  @ApiProperty({ description: "판매자 설명", example: "최고의 가구 전문 판매점" })
  @IsString()
  description: string;

  @ApiProperty({
    description: "판매자 로고 URL",
    example: "https://example.com/sellers/homefurniture.png",
  })
  @IsUrl()
  logo_url: string;

  @ApiProperty({ description: "판매자 평점", example: 4.8 })
  @IsNumber()
  @Min(0)
  rating: number;

  @ApiProperty({ description: "판매자 연락 이메일", example: "contact@homefurniture.com" })
  @IsEmail()
  contact_email: string;

  @ApiProperty({ description: "판매자 연락 전화번호", example: "02-1234-5678" })
  @Matches(/^0\d{1,2}-\d{3,4}-\d{4}$/, {
    message: "전화번호 형식은 02-1234-5678 또는 010-1234-5678이어야 합니다.",
  })
  contact_phone: string;
}
