import express from 'express';
import { mainController } from '../controllers/main.controller';

const router = express.Router();

// 메인 페이지 상품 및 카테고리 목록 조회
router.get('/', mainController.getMainPageData);

export default router; 