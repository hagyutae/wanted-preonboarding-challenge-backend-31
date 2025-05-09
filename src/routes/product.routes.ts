import express from 'express';
import { productController } from '../controllers/product.controller';

const router = express.Router();

/**
 * @route GET /api/products
 * @desc 상품 목록 조회 (검색, 필터링 포함)
 * @access Public
 */
router.get('/', productController.getProducts);

/**
 * @route GET /api/products/:id
 * @desc 상품 상세 정보 조회
 * @access Public
 */
router.get('/:id', productController.getProductById);

/**
 * @route POST /api/products
 * @desc 새 상품 등록 (인증 필요)
 * @access Private
 */
router.post('/', productController.createProduct);

/**
 * @route PUT /api/products/:id
 * @desc 상품 정보 수정 (인증 필요)
 * @access Private
 */
router.put('/:id', productController.updateProduct);

/**
 * @route DELETE /api/products/:id
 * @desc 상품 삭제 (인증 필요)
 * @access Private
 */
router.delete('/:id', productController.deleteProduct);

/**
 * @route POST /api/products/:id/options
 * @desc 상품 옵션 추가 (인증 필요)
 * @access Private
 */
router.post('/:id/options', productController.addProductOption);

/**
 * @route PUT /api/products/:id/options/:optionId
 * @desc 상품 옵션 수정 (인증 필요)
 * @access Private
 */
router.put('/:id/options/:optionId', productController.updateProductOption);

/**
 * @route DELETE /api/products/:id/options/:optionId
 * @desc 상품 옵션 삭제 (인증 필요)
 * @access Private
 */
router.delete('/:id/options/:optionId', productController.deleteProductOption);

/**
 * @route POST /api/products/:id/images
 * @desc 상품 이미지 추가 (인증 필요)
 * @access Private
 */
router.post('/:id/images', productController.addProductImage);

/**
 * @route GET /api/products/:id/reviews
 * @desc 상품 리뷰 목록 조회 (페이지네이션 지원)
 * @access Public
 */
router.get('/:id/reviews', productController.getProductReviews);

/**
 * @route POST /api/products/:id/reviews
 * @desc 상품 리뷰 작성 (인증 필요)
 * @access Private
 */
router.post('/:id/reviews', productController.createProductReview);

export default router;
