import { Request, Response } from 'express';
import { categoryService } from '../services/category.service';
import { parsePaginationParams } from '../utils/pagination';

export const categoryController = {
  /**
   * 모든 카테고리 조회
   */
  async getAllCategories(req: Request, res: Response) {
    try {
      const level = req.query.level ? parseInt(req.query.level as string) : undefined;
      const categories = await categoryService.getAllCategories(level);
      
      res.json({
        success: true,
        data: categories,
        message: "카테고리 목록을 성공적으로 조회했습니다."
      });
    } catch (error) {
      console.error('카테고리 목록 조회 오류:', error);
      res.status(500).json({
        success: false,
        error: {
          code: "INTERNAL_ERROR",
          message: "카테고리 목록을 가져오는 중 오류가 발생했습니다."
        }
      });
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

      const result = await categoryService.getCategoryProducts(
        categoryId,
        paginationParams,
        includeSubcategories
      );
      
      res.json({
        success: true,
        data: {
          category: result.category,
          items: result.products,
          pagination: {
            total_items: result.total,
            total_pages: Math.ceil(result.total / result.perPage),
            current_page: result.page,
            per_page: result.perPage
          }
        },
        message: "카테고리 상품 목록을 성공적으로 조회했습니다."
      });
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '카테고리별 상품 목록을 가져오는 중 오류가 발생했습니다.';
      const status = errorMessage === '카테고리를 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? "RESOURCE_NOT_FOUND" : "INTERNAL_ERROR";
      
      console.error('카테고리별 상품 목록 조회 오류:', error);
      res.status(status).json({
        success: false,
        error: {
          code: errorCode,
          message: errorMessage
        }
      });
    }
  }
}; 