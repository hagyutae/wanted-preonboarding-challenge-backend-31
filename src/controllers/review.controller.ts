import { Request, Response } from 'express';
import { reviewService } from '../services/review.service';
import { parsePaginationParams, getPaginationResult } from '../utils/pagination';

export const reviewController = {
  /**
   * 모든 리뷰 목록 조회 컨트롤러
   * @param req - Express 요청 객체 (page, perPage 쿼리 파라미터 지원)
   * @param res - Express 응답 객체
   * @returns 페이지네이션된 리뷰 목록 응답
   */
  async getAllReviews(req: Request, res: Response) {
    try {
      // 페이지네이션 파라미터 파싱
      const paginationParams = parsePaginationParams(req.query);

      // 기본 상품 ID 사용 (실제로는 모든 상품의 리뷰를 조회하는 API를 별도로 구현해야 함)
      const productId = parseInt(req.query.productId as string) || 1;

      const result = await reviewService.getReviews(productId, paginationParams);

      res.json({
        status: 'success',
        data: result.items,
        summary: result.summary,
        pagination: result.pagination,
      });
    } catch (error) {
      console.error('리뷰 목록 조회 오류:', error);
      res.status(500).json({ message: '리뷰 목록을 가져오는 중 오류가 발생했습니다.' });
    }
  },

  /**
   * 특정 ID의 리뷰 조회 컨트롤러
   * @param req - Express 요청 객체 (id 파라미터 필요)
   * @param res - Express 응답 객체
   * @returns 단일 리뷰 정보 응답
   */
  async getReviewById(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);
      const review = await reviewService.getReviewById(reviewId);

      res.json({
        status: 'success',
        data: review,
      });
    } catch (error) {
      const message =
        error instanceof Error ? error.message : '리뷰를 가져오는 중 오류가 발생했습니다.';
      const status = message === '리뷰를 찾을 수 없습니다.' ? 404 : 500;

      console.error('리뷰 조회 오류:', error);
      res.status(status).json({ message });
    }
  },

  /**
   * 리뷰 수정 컨트롤러
   * @param req - Express 요청 객체
   *   - id: 수정할 리뷰 ID (URL 파라미터)
   *   - body: 수정할 리뷰 데이터 (rating, title, content)
   * @param res - Express 응답 객체
   * @returns 수정된 리뷰 정보 응답
   */
  async updateReview(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);
      const { rating, title, content } = req.body;

      // 임시 변수: 실제 구현에서는 토큰에서 가져와야 함
      const userId = 1;

      // 데이터 입력 검증
      if (!rating || rating < 1 || rating > 5) {
        return res.status(400).json({
          success: false,
          error: {
            code: 'INVALID_INPUT',
            message: '평점은 1-5 사이의 숫자로 입력해주세요.',
          },
        });
      }

      // 리뷰 업데이트
      const updatedReview = await reviewService.updateReview(reviewId, userId, {
        rating,
        title,
        content,
      });

      // 성공 응답
      res.json({
        success: true,
        data: {
          id: updatedReview.id,
          rating: updatedReview.rating,
          title: updatedReview.title,
          content: updatedReview.content,
          updated_at: updatedReview.updated_at,
        },
        message: '리뷰가 성공적으로 수정되었습니다.',
      });
    } catch (error) {
      // 에러 처리
      const errorMessage =
        error instanceof Error ? error.message : '리뷰 수정 중 오류가 발생했습니다.';

      let status = 500;
      let errorCode = 'INTERNAL_ERROR';

      if (errorMessage === '리뷰를 찾을 수 없습니다.') {
        status = 404;
        errorCode = 'RESOURCE_NOT_FOUND';
      } else if (errorMessage === '다른 사용자의 리뷰를 수정할 권한이 없습니다.') {
        status = 403;
        errorCode = 'FORBIDDEN';
      }

      res.status(status).json({
        success: false,
        error: {
          code: errorCode,
          message: errorMessage,
        },
      });
    }
  },

  /**
   * 리뷰 삭제 컨트롤러
   * @param req - Express 요청 객체 (id 파라미터 필요)
   * @param res - Express 응답 객체
   * @returns 삭제 성공 응답
   */
  async deleteReview(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);

      // 임시 변수: 실제 구현에서는 토큰에서 가져와야 함
      const userId = 1;

      // 리뷰 삭제
      await reviewService.deleteReview(reviewId, userId);

      // 성공 응답
      res.json({
        success: true,
        data: null,
        message: '리뷰가 성공적으로 삭제되었습니다.',
      });
    } catch (error) {
      // 에러 처리
      const errorMessage =
        error instanceof Error ? error.message : '리뷰 삭제 중 오류가 발생했습니다.';

      let status = 500;
      let errorCode = 'INTERNAL_ERROR';

      if (errorMessage === '리뷰를 찾을 수 없습니다.') {
        status = 404;
        errorCode = 'RESOURCE_NOT_FOUND';
      } else if (errorMessage === '다른 사용자의 리뷰를 삭제할 권한이 없습니다.') {
        status = 403;
        errorCode = 'FORBIDDEN';
      }

      res.status(status).json({
        success: false,
        error: {
          code: errorCode,
          message: errorMessage,
        },
      });
    }
  },
};
