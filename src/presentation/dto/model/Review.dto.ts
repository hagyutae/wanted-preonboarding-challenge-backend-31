import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsDate, IsInt, IsString, IsUrl, Min, ValidateNested } from "class-validator";

class UserDTO {
  @ApiProperty({ description: "사용자 ID", example: 250 })
  @IsInt()
  id?: number;

  @ApiProperty({ description: "사용자 이름", example: "홍길동" })
  @IsString()
  name?: string;

  @ApiProperty({
    description: "사용자 아바타 URL",
    example: "https://example.com/avatars/user250.jpg",
  })
  @IsUrl()
  avatar_url?: string;
}

export default class ReviewDTO {
  @ApiProperty({ description: "리뷰 ID", example: 1500 })
  @IsInt()
  id?: number;

  @ApiProperty({ description: "리뷰 작성자", type: UserDTO })
  @ValidateNested()
  @Type(() => UserDTO)
  user?: UserDTO;

  @ApiProperty({ description: "평점", example: 5 })
  @IsInt()
  @Min(0)
  rating: number;

  @ApiProperty({ description: "리뷰 제목", example: "완벽한 소파입니다!" })
  @IsString()
  title?: string;

  @ApiProperty({
    description: "리뷰 내용",
    example: "배송도 빠르고 품질도 매우 좋습니다. 색상도 사진과 동일하고 조립도 쉬웠어요.",
  })
  @IsString()
  content?: string;

  @ApiProperty({ description: "생성 일시", example: "2025-04-12T15:30:00Z" })
  @IsDate()
  created_at?: Date;

  @ApiProperty({ description: "수정 일시", example: "2025-04-12T15:30:00Z" })
  @IsDate()
  updated_at?: Date;

  @ApiProperty({ description: "구매 여부", example: true })
  @IsBoolean()
  verified_purchase?: boolean;

  @ApiProperty({ description: "유용한 리뷰 점수", example: 12 })
  @IsInt()
  @Min(0)
  helpful_votes?: number;
}
