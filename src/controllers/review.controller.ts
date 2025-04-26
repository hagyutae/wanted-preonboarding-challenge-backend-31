import { Request, Response } from 'express';
import { reviewService } from '../services/review.service';
import { parsePaginationParams, getPaginationResult } from '../utils/pagination';

export const reviewController = {
  /**
   * 모든 리뷰 조회
   */
  async getAllReviews(req: Request, res: Response) {
    try {
      // 페이지네이션 파라미터 파싱
      const paginationParams = parsePaginationParams(req.query);
      const result = await reviewService.getAllReviews(paginationParams);
      
      res.json({
        status: 'success',
        ...getPaginationResult(result.reviews, result.total, result.page, result.perPage)
      });
    } catch (error) {
      console.error('리뷰 목록 조회 오류:', error);
      res.status(500).json({ message: '리뷰 목록을 가져오는 중 오류가 발생했습니다.' });
    }
  },

  /**
   * 특정 리뷰 조회
   */
  async getReviewById(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);
      const review = await reviewService.getReviewById(reviewId);
      
      res.json({
        status: 'success',
        data: review
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : '리뷰를 가져오는 중 오류가 발생했습니다.';
      const status = message === '리뷰를 찾을 수 없습니다.' ? 404 : 500;
      
      console.error('리뷰 조회 오류:', error);
      res.status(status).json({ message });
    }
  },

  /**
   * 리뷰 수정
   */
  async updateReview(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);
      // TODO: 사용자 인증 구현
      const userId = req.body.userId || 1; // 임시로 사용자 ID 설정
      
      const review = await reviewService.updateReview(reviewId, userId, req.body);
      
      res.json({
        status: 'success',
        data: review
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : '리뷰 수정 중 오류가 발생했습니다.';
      const status = message.includes('권한이 없습니다') ? 403 : 
                    message === '리뷰를 찾을 수 없습니다.' ? 404 : 500;
      
      console.error('리뷰 수정 오류:', error);
      res.status(status).json({ message });
    }
  },

  /**
   * 리뷰 삭제
   */
  async deleteReview(req: Request, res: Response) {
    try {
      const reviewId = parseInt(req.params.id);
      // TODO: 사용자 인증 구현
      const userId = req.body.userId || 1; // 임시로 사용자 ID 설정
      
      await reviewService.deleteReview(reviewId, userId);
      
      res.json({
        status: 'success',
        message: '리뷰가 성공적으로 삭제되었습니다.'
      });
    } catch (error) {
      const message = error instanceof Error ? error.message : '리뷰 삭제 중 오류가 발생했습니다.';
      const status = message.includes('권한이 없습니다') ? 403 : 
                    message === '리뷰를 찾을 수 없습니다.' ? 404 : 500;
      
      console.error('리뷰 삭제 오류:', error);
      res.status(status).json({ message });
    }
  }
}; 