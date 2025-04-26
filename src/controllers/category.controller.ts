import { Request, Response } from 'express';
import { categoryService } from '../services/category.service';
import { parsePaginationParams, getPaginationResult } from '../utils/pagination';

export const categoryController = {
  /**
   * 모든 카테고리 조회
   */
  async getAllCategories(req: Request, res: Response) {
    try {
      const categories = await categoryService.getAllCategories();
      
      res.json({
        status: 'success',
        data: categories
      });
    } catch (error) {
      console.error('카테고리 목록 조회 오류:', error);
      res.status(500).json({ message: '카테고리 목록을 가져오는 중 오류가 발생했습니다.' });
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
        status: 'success',
        ...getPaginationResult(result.products, result.total, result.page, result.perPage)
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : '카테고리별 상품 목록을 가져오는 중 오류가 발생했습니다.';
      const status = message === '카테고리를 찾을 수 없습니다.' ? 404 : 500;
      
      console.error('카테고리별 상품 목록 조회 오류:', error);
      res.status(status).json({ message });
    }
  }
}; 