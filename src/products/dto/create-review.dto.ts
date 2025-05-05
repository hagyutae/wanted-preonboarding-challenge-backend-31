import { ApiProperty } from '@nestjs/swagger';
import { Type } from 'class-transformer';
import { IsString, IsInt, Min, Max, IsBoolean } from 'class-validator';

export class CreateReviewDto {
  @ApiProperty({ example: 5, description: '평점(1~5)' })
  @IsInt()
  @Type(() => Number)
  @Min(1)
  @Max(5)
  rating: number;

  @ApiProperty({ example: '정말 좋은 상품입니다!', description: '리뷰 제목' })
  @IsString()
  title: string;

  @ApiProperty({
    example: '배송도 빠르고 품질도 좋아요.',
    description: '리뷰 내용',
  })
  @IsString()
  content: string;

  @ApiProperty({ example: true, description: '구매 인증 여부' })
  @IsBoolean()
  verified_purchase: boolean;

  @ApiProperty({ example: 1, description: '작성자(유저) ID' })
  @IsInt()
  @Type(() => Number)
  userId: number;
}
