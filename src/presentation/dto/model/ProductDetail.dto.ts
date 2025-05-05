import { ApiProperty } from "@nestjs/swagger";
import { Type } from "class-transformer";
import { IsBoolean, IsNumber, IsString, Matches, Min, ValidateNested } from "class-validator";

export class DimensionsDTO {
  @ApiProperty({ description: "가로 길이", example: 200 })
  @IsNumber()
  @Min(0)
  width: number;

  @ApiProperty({ description: "세로 길이", example: 85 })
  @IsNumber()
  @Min(0)
  height: number;

  @ApiProperty({ description: "깊이", example: 90 })
  @IsNumber()
  @Min(0)
  depth: number;
}

export class AdditionalInfoDTO {
  @ApiProperty({ description: "조립 필요 여부", example: true })
  @IsBoolean()
  assembly_required: boolean;

  @ApiProperty({ description: "조립 시간", example: "30분" })
  @Matches(/^(\d+시간)?(\d+분)?(\d+초)?$/, {
    message: "조립 시간 형식은 'X시간 Y분 Z초'이어야 합니다.",
  })
  assembly_time: string;
}

export default class ProductDetailDTO {
  @ApiProperty({ description: "무게", example: 25.5 })
  @IsNumber()
  @Min(0)
  weight: number;

  @ApiProperty({ description: "크기 정보", type: DimensionsDTO })
  @ValidateNested()
  @Type(() => DimensionsDTO)
  dimensions: DimensionsDTO;

  @ApiProperty({ description: "재료", example: "가죽, 목재, 폼" })
  @IsString()
  materials: string;

  @ApiProperty({ description: "원산지", example: "대한민국" })
  @IsString()
  country_of_origin: string;

  @ApiProperty({ description: "보증 정보", example: "2년 품질 보증" })
  @IsString()
  warranty_info: string;

  @ApiProperty({ description: "관리 지침", example: "마른 천으로 표면을 닦아주세요" })
  @IsString()
  care_instructions: string;

  @ApiProperty({ description: "추가 정보", type: AdditionalInfoDTO })
  @ValidateNested()
  @Type(() => AdditionalInfoDTO)
  additional_info: AdditionalInfoDTO;
}
