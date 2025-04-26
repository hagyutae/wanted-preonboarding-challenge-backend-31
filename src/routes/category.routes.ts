import express from 'express';
import { categoryController } from '../controllers/category.controller';

const router = express.Router();

// 카테고리 목록 조회 (계층 구조 포함)
router.get('/', categoryController.getAllCategories);

// 특정 카테고리의 상품 목록 조회
router.get('/:id/products', categoryController.getCategoryProducts);

export default router;