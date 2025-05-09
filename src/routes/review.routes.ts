import express from 'express';
import { reviewController } from '../controllers/review.controller';

const router = express.Router();

/**
 * @route GET /api/reviews
 * @desc 모든 리뷰 목록 조회 (페이지네이션 지원)
 * @access Public
 */
router.get('/', reviewController.getAllReviews);

/**
 * @route GET /api/reviews/:id
 * @desc 특정 ID의 리뷰 상세 조회
 * @access Public
 */
router.get('/:id', reviewController.getReviewById);

/**
 * @route PUT /api/reviews/:id
 * @desc 특정 리뷰 수정 (인증 필요)
 * @access Private
 */
router.put('/:id', reviewController.updateReview);

/**
 * @route DELETE /api/reviews/:id
 * @desc 특정 리뷰 삭제 (인증 필요)
 * @access Private
 */
router.delete('/:id', reviewController.deleteReview);

export default router;
