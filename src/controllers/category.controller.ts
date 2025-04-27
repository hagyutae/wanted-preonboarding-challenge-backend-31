import { Request, Response } from 'express';
import { categoryService } from '../services/category.service';
import { parsePaginationParams } from '../utils/pagination';
import { createSuccessResponse, createPaginatedResponse, createErrorResponse, ErrorCode } from '../utils/response';

export const categoryController = {
  /**
   * 모든 카테고리 조회
   */
  async getAllCategories(req: Request, res: Response) {
    try {
      const level = req.query.level ? parseInt(req.query.level as string) : undefined;
      const categories = await categoryService.getAllCategories(level);
      
      return res.json(
        createSuccessResponse(
          categories,
          "카테고리 목록을 성공적으로 조회했습니다."
        )
      );
    } catch (error) {
      console.error('카테고리 목록 조회 오류:', error);
      return res.status(500).json(
        createErrorResponse({
          code: ErrorCode.INTERNAL_ERROR,
          message: "카테고리 목록을 가져오는 중 오류가 발생했습니다."
        })
      );
    }
  },

  /**
   * 특정 카테고리의 상품 목록 조회
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
            message: "유효하지 않은 카테고리 ID입니다."
          })
        );
      }

      const result = await categoryService.getCategoryProducts(
        categoryId,
        paginationParams,
        includeSubcategories
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
              per_page: result.perPage
            }
          },
          "카테고리 상품 목록을 성공적으로 조회했습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '카테고리별 상품 목록을 가져오는 중 오류가 발생했습니다.';
      const status = errorMessage === '카테고리를 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('카테고리별 상품 목록 조회 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  }
}; 