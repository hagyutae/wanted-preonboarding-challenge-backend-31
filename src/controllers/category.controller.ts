import { Request, Response } from 'express';
import { categoryService } from '../services/category.service';
import { parsePaginationParams } from '../utils/pagination';
import {
  createSuccessResponse,
  createPaginatedResponse,
  createErrorResponse,
  ErrorCode,
} from '../utils/response';

export const categoryController = {
  /**
   * 모든 카테고리 목록 조회 컨트롤러
   * @param req - Express 요청 객체 (level 쿼리 파라미터로 특정 레벨의 카테고리만 필터링 가능)
   * @param res - Express 응답 객체
   * @returns 카테고리 목록을 포함한 응답
   */
  async getAllCategories(req: Request, res: Response) {
    try {
      let level: number | undefined = undefined;

      if (req.query.level) {
        const levelParam = req.query.level;
        if (typeof levelParam === 'string') {
          const parsedLevel = parseInt(levelParam);
          if (!isNaN(parsedLevel)) {
            level = parsedLevel;
          }
        }
      }

      const categories = await categoryService.getAllCategories(level);

      return res.json(
        createSuccessResponse(categories, '카테고리 목록을 성공적으로 조회했습니다.'),
      );
    } catch (error) {
      console.error('카테고리 목록 조회 오류:', error);
      return res.status(500).json(
        createErrorResponse({
          code: ErrorCode.INTERNAL_ERROR,
          message: '카테고리 목록을 가져오는 중 오류가 발생했습니다.',
        }),
      );
    }
  },

  /**
   * 특정 카테고리의 상품 목록 조회 컨트롤러
   * @param req - Express 요청 객체
   *   - id: 카테고리 ID (URL 파라미터)
   *   - includeSubcategories: 하위 카테고리 포함 여부 (쿼리 파라미터, 기본값: true)
   *   - page, perPage: 페이지네이션 정보 (쿼리 파라미터)
   * @param res - Express 응답 객체
   * @returns 카테고리 정보와 상품 목록을 포함한 페이지네이션 응답
   */
  async getCategoryProducts(req: Request, res: Response) {
    try {
      const categoryId = parseInt(req.params.id);

      // 쿼리 파라미터 파싱
      const paginationParams = parsePaginationParams(req.query);
      const includeSubcategories = req.query.includeSubcategories !== 'false';

      // 유효성 검사
      if (!categoryId || isNaN(categoryId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: '유효하지 않은 카테고리 ID입니다.',
          }),
        );
      }

      const result = await categoryService.getCategoryProducts(
        categoryId,
        paginationParams,
        includeSubcategories,
      );

      return res.json(
        createSuccessResponse(
          {
            category: result.category,
            items: result.products,
            pagination: {
              total_items: result.total,
              total_pages: result.totalPages,
              current_page: result.page,
              per_page: result.perPage,
            },
          },
          '카테고리 상품 목록을 성공적으로 조회했습니다.',
        ),
      );
    } catch (error) {
      const errorMessage =
        error instanceof Error
          ? error.message
          : '카테고리별 상품 목록을 가져오는 중 오류가 발생했습니다.';
      const status = errorMessage === '카테고리를 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;

      console.error('카테고리별 상품 목록 조회 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage,
        }),
      );
    }
  },
};
