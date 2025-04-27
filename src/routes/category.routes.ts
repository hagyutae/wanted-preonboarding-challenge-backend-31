import express from 'express';
import { categoryController } from '../controllers/category.controller';

const router = express.Router();

/**
 * @route GET /api/categories
 * @desc 모든 카테고리 목록 조회 (계층 구조 포함, 레벨별 필터링 가능)
 * @access Public
 */
router.get('/', categoryController.getAllCategories);

/**
 * @route GET /api/categories/:id/products
 * @desc 특정 카테고리의 상품 목록 조회 (하위 카테고리 포함 옵션, 페이지네이션 지원)
 * @access Public
 */
router.get('/:id/products', categoryController.getCategoryProducts);

export default router;
