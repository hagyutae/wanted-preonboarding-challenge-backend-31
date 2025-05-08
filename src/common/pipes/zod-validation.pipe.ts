import { PipeTransform, Injectable, ArgumentMetadata } from '@nestjs/common';
import { ZodSchema, ZodError } from 'zod';
import { ApiException } from '../exceptions/api.exception';
import { ErrorCode } from '../types/response.type';

/**
 * Zod 스키마를 이용한 검증 파이프
 */
@Injectable()
export class ZodValidationPipe implements PipeTransform {
  constructor(private schema: ZodSchema) {}

  /**
   * ZodError를 일관된 형식으로 변환
   */
  private formatZodError(error: ZodError): Record<string, any> {
    const formattedErrors: Record<string, string[]> = {};

    // 에러 메시지를 필드별로 분류
    for (const issue of error.errors) {
      const path = issue.path.join('.');
      const fieldName = path || 'general';

      if (!formattedErrors[fieldName]) {
        formattedErrors[fieldName] = [];
      }

      formattedErrors[fieldName].push(issue.message);
    }

    return {
      validation_errors: formattedErrors,
    };
  }

  transform(value: unknown, metadata: ArgumentMetadata) {
    try {
      // Zod 스키마로 검증
      return this.schema.parse(value);
    } catch (error) {
      if (error instanceof ZodError) {
        // ZodError를 표준화된 API 예외로 변환
        const details = this.formatZodError(error);
        throw new ApiException(
          ErrorCode.INVALID_INPUT,
          '입력 데이터 검증에 실패했습니다.',
          details,
        );
      }

      // 기타 예외 그대로 발생
      throw error;
    }
  }
}
