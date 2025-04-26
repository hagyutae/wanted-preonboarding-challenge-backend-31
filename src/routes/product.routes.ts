import express from 'express';
import { productController } from '../controllers/product.controller';

const router = express.Router();

// 상품 목록 조회 (검색, 필터링 포함)
router.get('/', productController.getProducts);

// 상품 상세 조회
router.get('/:id', productController.getProductById);

// 상품 등록
router.post('/', productController.createProduct);

// 상품 수정
router.put('/:id', productController.updateProduct);

// 상품 삭제
router.delete('/:id', productController.deleteProduct);

// 상품 옵션 추가
router.post('/:id/options', productController.addProductOption);

// 상품 옵션 수정
router.put('/:id/options/:optionId', productController.updateProductOption);

// 상품 옵션 삭제
router.delete('/:id/options/:optionId', productController.deleteProductOption);

// 상품 이미지 추가
router.post('/:id/images', productController.addProductImage);

// 상품 리뷰 조회
router.get('/:id/reviews', productController.getProductReviews);

// 상품 리뷰 작성
router.post('/:id/reviews', productController.createProductReview);

export default router; 