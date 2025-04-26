import express from 'express';
import { reviewController } from '../controllers/review.controller';

const router = express.Router();

// 모든 리뷰 조회
router.get('/', reviewController.getAllReviews);

// 특정 리뷰 조회
router.get('/:id', reviewController.getReviewById);

// 리뷰 수정
router.put('/:id', reviewController.updateReview);

// 리뷰 삭제
router.delete('/:id', reviewController.deleteReview);

export default router;