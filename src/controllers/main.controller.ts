import { Request, Response } from 'express';
import { mainService } from '../services/main.service';
import { createSuccessResponse, createErrorResponse, ErrorCode } from '../utils/response';
import { MainPageDataResponse, ApiResponse } from '../types';

export const mainController = {
  /**
   * 메인 페이지 데이터 조회 컨트롤러
   * @param req - Express 요청 객체
   * @param res - Express 응답 객체
   * @returns 메인 페이지에 필요한 신규 상품, 인기 상품, 주요 카테고리 데이터를 포함한 응답
   */
  async getMainPageData(
    req: Request,
    res: Response,
  ): Promise<Response<ApiResponse<MainPageDataResponse>>> {
    try {
      // 각 서비스를 병렬로 호출하여 성능 최적화
      const [newProducts, popularProducts, featuredCategories] = await Promise.all([
        mainService.getNewProducts(),
        mainService.getPopularProducts(),
        mainService.getFeaturedCategories(),
      ]);

      // 메인 페이지 데이터 구성
      const mainPageData: MainPageDataResponse = {
        new_products: newProducts,
        popular_products: popularProducts,
        featured_categories: featuredCategories,
      };

      return res.json(
        createSuccessResponse(mainPageData, '메인 페이지 데이터를 성공적으로 조회했습니다.'),
      );
    } catch (error) {
      console.error('메인 페이지 데이터 조회 중 오류 발생:', error);
      return res.status(500).json(
        createErrorResponse({
          code: ErrorCode.INTERNAL_ERROR,
          message: '서버 오류가 발생했습니다.',
        }),
      );
    }
  },
};
