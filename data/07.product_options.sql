-- 제품 옵션 그룹 데이터 삽입 (id 값 지정)
INSERT INTO product_option_groups (id, product_id, name, display_order) VALUES
-- 1. 슈퍼 편안한 소파 옵션 그룹
(1, 1, '색상', 1),
(2, 1, '소재', 2),

-- 2. 모던 디자인 책상 옵션 그룹
(3, 2, '색상', 1),
(4, 2, '사이즈', 2),

-- 3. 클래식 4인용 식탁 세트 옵션 그룹
(5, 3, '색상', 1),

-- 4. 편안한 침대 프레임 옵션 그룹
(6, 4, '사이즈', 1),
(7, 4, '색상', 2),

-- 5. 모던 책장 옵션 그룹
(8, 5, '색상', 1),
(9, 5, '층수', 2),

-- 6. 럭셔리 옷장 옵션 그룹
(10, 6, '색상', 1),
(11, 6, '사이즈', 2),

-- 7. 인체공학적 사무용 의자 옵션 그룹
(12, 7, '색상', 1),

-- 8. 아일랜드 주방 테이블 옵션 그룹
(13, 8, '색상', 1),
(14, 8, '상판 소재', 2),

-- 9. 패브릭 1인 소파 옵션 그룹
(15, 9, '색상', 1),

-- 10. 원목 거실장 옵션 그룹
(16, 10, '색상', 1),
(17, 10, '사이즈', 2),

-- 11. 스마트 냉장고 옵션 그룹
(18, 11, '색상', 1),
(19, 11, '용량', 2),

-- 12. 고효율 세탁기 옵션 그룹
(20, 12, '색상', 1),
(21, 12, '용량', 2),

-- 13. 4K UHD 스마트 TV 옵션 그룹
(22, 13, '화면 크기', 1),
(23, 13, '벽걸이 옵션', 2),

-- 14. 초강력 무선 청소기 옵션 그룹
(24, 14, '색상', 1),
(25, 14, '배터리 옵션', 2),

-- 15. 인버터 에어컨 옵션 그룹
(26, 15, '용량', 1),
(27, 15, '설치 옵션', 2),

-- 16. 스팀 다리미 옵션 그룹
(28, 16, '색상', 1),

-- 17. 초고속 블렌더 옵션 그룹
(29, 17, '용량', 1),
(30, 17, '색상', 2),

-- 18. 전자동 커피머신 옵션 그룹
(31, 18, '색상', 1),
(32, 18, '기능', 2),

-- 19. 스마트 전기밥솥 옵션 그룹
(33, 19, '용량', 1),
(34, 19, '색상', 2),

-- 20. 공기청정기 옵션 그룹
(35, 20, '적용면적', 1),
(36, 20, '색상', 2),

-- 21. 방수 등산 텐트 옵션 그룹
(37, 21, '색상', 1),
(38, 21, '인원', 2),

-- 22. 초경량 침낭 옵션 그룹
(39, 22, '색상', 1),
(40, 22, '온도 등급', 2),

-- 23. 프로페셔널 등산화 옵션 그룹
(41, 23, '색상', 1),
(42, 23, '사이즈', 2),

-- 24. 전문가용 자전거 옵션 그룹
(43, 24, '색상', 1),
(44, 24, '프레임 사이즈', 2),

-- 25. 기능성 스포츠 웨어 세트 옵션 그룹
(45, 25, '색상', 1),
(46, 25, '사이즈', 2),

-- 26. 스마트 피트니스 워치 옵션 그룹
(47, 26, '색상', 1),
(48, 26, '스트랩 재질', 2),

-- 27. 프리미엄 요가 매트 옵션 그룹
(49, 27, '색상', 1),
(50, 27, '두께', 2),

-- 28. 접이식 캠핑 테이블 옵션 그룹
(51, 28, '사이즈', 1),
(52, 28, '색상', 2),

-- 29. 전문가용 수영 고글 옵션 그룹
(53, 29, '색상', 1),
(54, 29, '렌즈 타입', 2),

-- 30. 홈트레이닝 덤벨 세트 옵션 그룹
(55, 30, '중량 세트', 1),
(56, 30, '그립 타입', 2);

-- 시퀀스 재설정
SELECT setval('product_option_groups_id_seq', (SELECT MAX(id) FROM product_option_groups));

-- 제품 옵션 데이터 삽입 (id 값 지정)
INSERT INTO product_options (id, option_group_id, name, additional_price, sku, stock, display_order) VALUES
-- 1. 슈퍼 편안한 소파 옵션
(1, 1, '브라운', 0, 'SOFA-BRN', 10, 1),
(2, 1, '블랙', 0, 'SOFA-BLK', 15, 2),
(3, 2, '천연 가죽', 100000, 'SOFA-LTHR', 5, 1),
(4, 2, '인조 가죽', 0, 'SOFA-FAKE', 20, 2),

-- 2. 모던 디자인 책상 옵션
(5, 3, '화이트', 0, 'DESK-WHT', 12, 1),
(6, 3, '블랙', 0, 'DESK-BLK', 8, 2),
(7, 4, '소형(90cm)', -30000, 'DESK-SML', 15, 1),
(8, 4, '중형(120cm)', 0, 'DESK-MED', 20, 2),
(9, 4, '대형(150cm)', 50000, 'DESK-LRG', 10, 3),

-- 3. 클래식 4인용 식탁 세트 옵션
(10, 5, '내추럴 브라운', 0, 'DSET-NBRN', 8, 1),
(11, 5, '월넛 다크', 20000, 'DSET-WDRK', 5, 2),
(12, 5, '화이트 오크', 40000, 'DSET-WOAK', 3, 3),

-- 4. 편안한 침대 프레임 옵션
(13, 6, '싱글(90cm)', -100000, 'BED-SGL', 12, 1),
(14, 6, '퀸(150cm)', 0, 'BED-QUN', 15, 2),
(15, 6, '킹(180cm)', 150000, 'BED-KNG', 8, 3),
(16, 7, '브라운', 0, 'BED-BRN', 20, 1),
(17, 7, '그레이', 0, 'BED-GRY', 18, 2),
(18, 7, '블랙', 0, 'BED-BLK', 12, 3),

-- 5. 모던 책장 옵션
(19, 8, '화이트', 0, 'BKSH-WHT', 15, 1),
(20, 8, '블랙', 0, 'BKSH-BLK', 12, 2),
(21, 8, '월넛', 10000, 'BKSH-WNT', 8, 3),
(22, 9, '3단', -30000, 'BKSH-3T', 10, 1),
(23, 9, '5단', 0, 'BKSH-5T', 15, 2),
(24, 9, '7단', 40000, 'BKSH-7T', 5, 3),

-- 6. 럭셔리 옷장 옵션
(25, 10, '내추럴 오크', 0, 'WRDB-NOAK', 5, 1),
(26, 10, '화이트', 0, 'WRDB-WHT', 7, 2),
(27, 10, '월넛 다크', 20000, 'WRDB-WDRK', 3, 3),
(28, 11, '2단 중형', -100000, 'WRDB-2MD', 8, 1),
(29, 11, '3단 대형', 0, 'WRDB-3LG', 5, 2),
(30, 11, '4단 특대형', 200000, 'WRDB-4XL', 2, 3),

-- 7. 인체공학적 사무용 의자 옵션
(31, 12, '블랙', 0, 'CHAIR-BLK', 25, 1),
(32, 12, '그레이', 0, 'CHAIR-GRY', 20, 2),
(33, 12, '블루', 10000, 'CHAIR-BLU', 15, 3),

-- 8. 아일랜드 주방 테이블 옵션
(34, 13, '화이트', 0, 'KISL-WHT', 10, 1),
(35, 13, '내추럴 우드', 20000, 'KISL-NWD', 8, 2),
(36, 13, '블랙', 10000, 'KISL-BLK', 6, 3),
(37, 14, '인조대리석', 0, 'KISL-FMRB', 12, 1),
(38, 14, '천연대리석', 150000, 'KISL-TMRB', 5, 2),
(39, 14, '우드', -30000, 'KISL-WOOD', 8, 3),

-- 9. 패브릭 1인 소파 옵션
(40, 15, '라이트 그레이', 0, 'SSOF-LGRY', 15, 1),
(41, 15, '다크 그레이', 0, 'SSOF-DGRY', 12, 2),
(42, 15, '베이지', 0, 'SSOF-BGE', 10, 3),
(43, 15, '민트', 10000, 'SSOF-MNT', 8, 4),

-- 10. 원목 거실장 옵션
(44, 16, '월넛', 0, 'TVST-WNT', 10, 1),
(45, 16, '오크', 0, 'TVST-OAK', 12, 2),
(46, 16, '체리', 20000, 'TVST-CHR', 8, 3),
(47, 17, '120cm', -50000, 'TVST-120', 15, 1),
(48, 17, '180cm', 0, 'TVST-180', 10, 2),
(49, 17, '240cm', 100000, 'TVST-240', 5, 3),

-- 11. 스마트 냉장고 옵션
(50, 18, '메탈 실버', 0, 'SMRF-MTSV', 10, 1),
(51, 18, '블랙 미러', 50000, 'SMRF-BKMR', 8, 2),
(52, 18, '화이트 글래스', 30000, 'SMRF-WTGL', 12, 3),
(53, 19, '600L', -100000, 'SMRF-600L', 15, 1),
(54, 19, '700L', 0, 'SMRF-700L', 10, 2),
(55, 19, '800L', 150000, 'SMRF-800L', 5, 3),

-- 12. 고효율 세탁기 옵션
(56, 20, '화이트', 0, 'WASH-WHT', 15, 1),
(57, 20, '메탈 실버', 20000, 'WASH-MTSV', 12, 2),
(58, 20, '블랙', 30000, 'WASH-BLK', 8, 3),
(59, 21, '12kg', -50000, 'WASH-12KG', 10, 1),
(60, 21, '15kg', 0, 'WASH-15KG', 15, 2),
(61, 21, '18kg', 80000, 'WASH-18KG', 5, 3),

-- 13. 4K UHD 스마트 TV 옵션
(62, 22, '43인치', -200000, 'TV-43', 20, 1),
(63, 22, '55인치', 0, 'TV-55', 15, 2),
(64, 22, '65인치', 300000, 'TV-65', 10, 3),
(65, 22, '75인치', 600000, 'TV-75', 5, 4),
(66, 23, '기본형', 0, 'TV-BASIC', 25, 1),
(67, 23, '벽걸이 브라켓 포함', 50000, 'TV-MOUNT', 20, 2),

-- 14. 초강력 무선 청소기 옵션
(68, 24, '메탈릭 실버', 0, 'VAC-MTSV', 20, 1),
(69, 24, '레드', 0, 'VAC-RED', 15, 2),
(70, 24, '블루', 0, 'VAC-BLUE', 12, 3),
(71, 25, '기본형(60분)', 0, 'VAC-STD', 25, 1),
(72, 25, '대용량(90분)', 80000, 'VAC-EXT', 15, 2),

-- 15. 인버터 에어컨 옵션
(73, 26, '13평형', -100000, 'AC-13', 15, 1),
(74, 26, '16평형', 0, 'AC-16', 20, 2),
(75, 26, '20평형', 150000, 'AC-20', 10, 3),
(76, 26, '23평형', 250000, 'AC-23', 5, 4),
(77, 27, '기본 설치', 0, 'AC-INST-STD', 30, 1),
(78, 27, '프리미엄 설치', 100000, 'AC-INST-PRE', 20, 2),

-- 16. 스팀 다리미 옵션
(79, 28, '블루', 0, 'IRON-BLUE', 20, 1),
(80, 28, '화이트', 0, 'IRON-WHT', 18, 2),
(81, 28, '로즈 핑크', 10000, 'IRON-PINK', 15, 3),

-- 17. 초고속 블렌더 옵션
(82, 29, '1.5L', -30000, 'BLND-1.5L', 20, 1),
(83, 29, '2.0L', 0, 'BLND-2.0L', 15, 2),
(84, 29, '2.5L', 40000, 'BLND-2.5L', 10, 3),
(85, 30, '메탈릭 실버', 0, 'BLND-MTSV', 25, 1),
(86, 30, '매트 블랙', 10000, 'BLND-MTBK', 20, 2),
(87, 30, '레드', 10000, 'BLND-RED', 15, 3),

-- 18. 전자동 커피머신 옵션
(88, 31, '블랙', 0, 'COFF-BLK', 12, 1),
(89, 31, '실버', 0, 'COFF-SLV', 10, 2),
(90, 31, '프리미엄 골드', 50000, 'COFF-GLD', 5, 3),
(91, 32, '기본형', 0, 'COFF-STD', 15, 1),
(92, 32, '라떼아트 기능', 100000, 'COFF-LATTE', 10, 2),
(93, 32, '더블 보일러', 200000, 'COFF-DBLB', 5, 3),

-- 19. 스마트 전기밥솥 옵션
(94, 33, '6인용', -40000, 'RICE-6', 20, 1),
(95, 33, '10인용', 0, 'RICE-10', 15, 2),
(96, 33, '15인용', 60000, 'RICE-15', 10, 3),
(97, 34, '화이트', 0, 'RICE-WHT', 20, 1),
(98, 34, '블랙', 0, 'RICE-BLK', 18, 2),
(99, 34, '로즈 골드', 30000, 'RICE-RSGD', 12, 3),

-- 20. 공기청정기 옵션
(100, 35, '20평형', -50000, 'AIRP-20', 15, 1),
(101, 35, '30평형', 0, 'AIRP-30', 20, 2),
(102, 35, '40평형', 80000, 'AIRP-40', 10, 3),
(103, 36, '화이트', 0, 'AIRP-WHT', 25, 1),
(104, 36, '그레이', 0, 'AIRP-GRY', 20, 2),
(105, 36, '블랙', 0, 'AIRP-BLK', 15, 3),

-- 21. 방수 등산 텐트 옵션
(106, 37, '그린', 0, 'TENT-GRN', 15, 1),
(107, 37, '블루', 0, 'TENT-BLU', 10, 2),
(108, 37, '오렌지', 0, 'TENT-ORG', 12, 3),
(109, 38, '2인용', -30000, 'TENT-2P', 20, 1),
(110, 38, '3인용', 0, 'TENT-3P', 15, 2),
(111, 38, '4인용', 50000, 'TENT-4P', 10, 3),

-- 22. 초경량 침낭 옵션
(112, 39, '네이비', 0, 'SLPB-NVY', 12, 1),
(113, 39, '블랙', 0, 'SLPB-BLK', 15, 2),
(114, 39, '레드', 0, 'SLPB-RED', 8, 3),
(115, 40, '0°C', -20000, 'SLPB-0C', 15, 1),
(116, 40, '-10°C', 0, 'SLPB-10C', 12, 2),
(117, 40, '-20°C', 40000, 'SLPB-20C', 8, 3),

-- 23. 프로페셔널 등산화 옵션
(118, 41, '브라운', 0, 'HKBT-BRN', 10, 1),
(119, 41, '그레이', 0, 'HKBT-GRY', 12, 2),
(120, 41, '블랙', 0, 'HKBT-BLK', 15, 3),
(121, 42, '250mm', 0, 'HKBT-250', 8, 1),
(122, 42, '260mm', 0, 'HKBT-260', 10, 2),
(123, 42, '270mm', 0, 'HKBT-270', 12, 3),
(124, 42, '280mm', 0, 'HKBT-280', 8, 4),

-- 24. 전문가용 자전거 옵션
(125, 43, '매트 블랙', 0, 'BIKE-MBLK', 8, 1),
(126, 43, '메탈릭 실버', 20000, 'BIKE-MSLV', 6, 2),
(127, 43, '레드', 30000, 'BIKE-RED', 5, 3),
(128, 44, 'S (15인치)', -100000, 'BIKE-15', 10, 1),
(129, 44, 'M (17인치)', 0, 'BIKE-17', 15, 2),
(130, 44, 'L (19인치)', 0, 'BIKE-19', 12, 3),
(131, 44, 'XL (21인치)', 80000, 'BIKE-21', 6, 4),

-- 25. 기능성 스포츠 웨어 세트 옵션
(132, 45, '블랙', 0, 'SPWT-BLK', 20, 1),
(133, 45, '네이비', 0, 'SPWT-NVY', 15, 2),
(134, 45, '그레이', 0, 'SPWT-GRY', 18, 3),
(135, 46, 'S', -5000, 'SPWT-S', 12, 1),
(136, 46, 'M', 0, 'SPWT-M', 20, 2),
(137, 46, 'L', 0, 'SPWT-L', 15, 3),
(138, 46, 'XL', 5000, 'SPWT-XL', 10, 4),

-- 26. 스마트 피트니스 워치 옵션
(139, 47, '블랙', 0, 'SWTC-BLK', 25, 1),
(140, 47, '실버', 0, 'SWTC-SLV', 20, 2),
(141, 47, '로즈 골드', 20000, 'SWTC-RSG', 15, 3),
(142, 48, '실리콘', 0, 'SWTC-SIL', 30, 1),
(143, 48, '나일론', 10000, 'SWTC-NYL', 20, 2),
(144, 48, '가죽', 30000, 'SWTC-LTH', 15, 3),

-- 27. 프리미엄 요가 매트 옵션
(145, 49, '퍼플', 0, 'YOGA-PPL', 20, 1),
(146, 49, '블루', 0, 'YOGA-BLU', 18, 2),
(147, 49, '핑크', 0, 'YOGA-PNK', 15, 3),
(148, 49, '그린', 0, 'YOGA-GRN', 12, 4),
(149, 50, '4mm', -10000, 'YOGA-4MM', 15, 1),
(150, 50, '6mm', 0, 'YOGA-6MM', 25, 2),
(151, 50, '8mm', 15000, 'YOGA-8MM', 10, 3),

-- 28. 접이식 캠핑 테이블 옵션
(152, 51, '소형 (80x60cm)', -15000, 'CTBL-SM', 20, 1),
(153, 51, '중형 (120x60cm)', 0, 'CTBL-MD', 15, 2),
(154, 51, '대형 (150x70cm)', 25000, 'CTBL-LG', 10, 3),
(155, 52, '실버', 0, 'CTBL-SLV', 25, 1),
(156, 52, '블랙', 5000, 'CTBL-BLK', 15, 2),
(157, 52, '우드 패턴', 10000, 'CTBL-WD', 10, 3),

-- 29. 전문가용 수영 고글 옵션
(158, 53, '블랙', 0, 'SWIM-BLK', 25, 1),
(159, 53, '블루', 0, 'SWIM-BLU', 20, 2),
(160, 53, '클리어', 0, 'SWIM-CLR', 15, 3),
(161, 54, '미러 코팅', 10000, 'SWIM-MIR', 18, 1),
(162, 54, '클리어 렌즈', 0, 'SWIM-CLR', 22, 2),
(163, 54, '변색 렌즈', 15000, 'SWIM-PHT', 15, 3),

-- 30. 홈트레이닝 덤벨 세트 옵션
(164, 55, '2.5-10kg 세트', -50000, 'DMBL-10', 15, 1),
(165, 55, '2.5-15kg 세트', 0, 'DMBL-15', 20, 2),
(166, 55, '2.5-25kg 세트', 80000, 'DMBL-25', 10, 3),
(167, 56, '고무 그립', 0, 'DMBL-RBR', 25, 1),
(168, 56, '크롬 그립', 15000, 'DMBL-CHR', 15, 2),
(169, 56, '논슬립 그립', 25000, 'DMBL-NSL', 10, 3);

-- 시퀀스 재설정
SELECT setval('product_options_id_seq', (SELECT MAX(id) FROM product_options));

-- 제품 이미지 데이터 삽입 (id 값 지정)
INSERT INTO product_images (id, product_id, url, alt_text, is_primary, display_order, option_id) VALUES
-- 1. 슈퍼 편안한 소파 이미지
(1, 1, 'http://example.com/images/default.png', '브라운 소파 정면', true, 1, 1),
(2, 1, 'http://example.com/images/default.png', '블랙 소파 정면', false, 2, 2),
(3, 1, 'http://example.com/images/default.png', '천연 가죽 소파 상세', false, 3, 3),
(4, 1, 'http://example.com/images/default.png', '인조 가죽 소파 상세', false, 4, 4),

-- 2. 모던 디자인 책상 이미지
(5, 2, 'http://example.com/images/default.png', '화이트 책상 전체 모습', true, 1, 5),
(6, 2, 'http://example.com/images/default.png', '블랙 책상 전체 모습', false, 2, 6),
(7, 2, 'http://example.com/images/default.png', '소형 책상 모습', false, 3, 7),
(8, 2, 'http://example.com/images/default.png', '중형 책상 모습', false, 4, 8),
(9, 2, 'http://example.com/images/default.png', '대형 책상 모습', false, 5, 9),

-- 3. 클래식 4인용 식탁 세트 이미지
(10, 3, 'http://example.com/images/default.png', '내추럴 브라운 식탁 세트', true, 1, 10),
(11, 3, 'http://example.com/images/default.png', '월넛 다크 식탁 세트', false, 2, 11),
(12, 3, 'http://example.com/images/default.png', '화이트 오크 식탁 세트', false, 3, 12),

-- 4. 편안한 침대 프레임 이미지
(13, 4, 'http://example.com/images/default.png', '싱글 사이즈 침대 프레임', false, 1, 13),
(14, 4, 'http://example.com/images/default.png', '퀸 사이즈 침대 프레임', true, 2, 14),
(15, 4, 'http://example.com/images/default.png', '킹 사이즈 침대 프레임', false, 3, 15),
(16, 4, 'http://example.com/images/default.png', '브라운 침대 프레임', false, 4, 16),
(17, 4, 'http://example.com/images/default.png', '그레이 침대 프레임', false, 5, 17),
(18, 4, 'http://example.com/images/default.png', '블랙 침대 프레임', false, 6, 18),

-- 5. 모던 책장 이미지
(19, 5, 'http://example.com/images/default.png', '화이트 책장', true, 1, 19),
(20, 5, 'http://example.com/images/default.png', '블랙 책장', false, 2, 20),
(21, 5, 'http://example.com/images/default.png', '월넛 책장', false, 3, 21),
(22, 5, 'http://example.com/images/default.png', '3단 책장', false, 4, 22),
(23, 5, 'http://example.com/images/default.png', '5단 책장', false, 5, 23),
(24, 5, 'http://example.com/images/default.png', '7단 책장', false, 6, 24),

-- 6. 럭셔리 옷장 이미지
(25, 6, 'http://example.com/images/default.png', '내추럴 오크 옷장', true, 1, 25),
(26, 6, 'http://example.com/images/default.png', '화이트 옷장', false, 2, 26),
(27, 6, 'http://example.com/images/default.png', '월넛 다크 옷장', false, 3, 27),
(28, 6, 'http://example.com/images/default.png', '2단 중형 옷장', false, 4, 28),
(29, 6, 'http://example.com/images/default.png', '3단 대형 옷장', false, 5, 29),
(30, 6, 'http://example.com/images/default.png', '4단 특대형 옷장', false, 6, 30),

-- 7. 인체공학적 사무용 의자 이미지
(31, 7, 'http://example.com/images/default.png', '블랙 사무용 의자', true, 1, 31),
(32, 7, 'http://example.com/images/default.png', '그레이 사무용 의자', false, 2, 32),
(33, 7, 'http://example.com/images/default.png', '블루 사무용 의자', false, 3, 33),

-- 8. 아일랜드 주방 테이블 이미지
(34, 8, 'http://example.com/images/default.png', '화이트 아일랜드 테이블', true, 1, 34),
(35, 8, 'http://example.com/images/default.png', '내추럴 우드 아일랜드 테이블', false, 2, 35),
(36, 8, 'http://example.com/images/default.png', '블랙 아일랜드 테이블', false, 3, 36),
(37, 8, 'http://example.com/images/default.png', '인조대리석 상판 아일랜드 테이블', false, 4, 37),
(38, 8, 'http://example.com/images/default.png', '천연대리석 상판 아일랜드 테이블', false, 5, 38),
(39, 8, 'http://example.com/images/default.png', '우드 상판 아일랜드 테이블', false, 6, 39),

-- 9. 패브릭 1인 소파 이미지
(40, 9, 'http://example.com/images/default.png', '라이트 그레이 1인 소파', true, 1, 40),
(41, 9, 'http://example.com/images/default.png', '다크 그레이 1인 소파', false, 2, 41),
(42, 9, 'http://example.com/images/default.png', '베이지 1인 소파', false, 3, 42),
(43, 9, 'http://example.com/images/default.png', '민트 1인 소파', false, 4, 43),

-- 10. 원목 거실장 이미지
(44, 10, 'http://example.com/images/default.png', '월넛 거실장', true, 1, 44),
(45, 10, 'http://example.com/images/default.png', '오크 거실장', false, 2, 45),
(46, 10, 'http://example.com/images/default.png', '체리 거실장', false, 3, 46),
(47, 10, 'http://example.com/images/default.png', '120cm 거실장', false, 4, 47),
(48, 10, 'http://example.com/images/default.png', '180cm 거실장', false, 5, 48),
(49, 10, 'http://example.com/images/default.png', '240cm 거실장', false, 6, 49),

-- 11. 스마트 냉장고 이미지
(50, 11, 'http://example.com/images/default.png', '메탈 실버 냉장고 정면', true, 1, 50),
(51, 11, 'http://example.com/images/default.png', '블랙 미러 냉장고 정면', false, 2, 51),
(52, 11, 'http://example.com/images/default.png', '화이트 글래스 냉장고 정면', false, 3, 52),
(53, 11, 'http://example.com/images/default.png', '600L 냉장고 내부', false, 4, 53),
(54, 11, 'http://example.com/images/default.png', '700L 냉장고 내부', false, 5, 54),
(55, 11, 'http://example.com/images/default.png', '800L 냉장고 내부', false, 6, 55),

-- 12. 고효율 세탁기 이미지
(56, 12, 'http://example.com/images/default.png', '화이트 세탁기 정면', true, 1, 56),
(57, 12, 'http://example.com/images/default.png', '메탈 실버 세탁기 정면', false, 2, 57),
(58, 12, 'http://example.com/images/default.png', '블랙 세탁기 정면', false, 3, 58),
(59, 12, 'http://example.com/images/default.png', '12kg 세탁기 내부', false, 4, 59),
(60, 12, 'http://example.com/images/default.png', '15kg 세탁기 내부', false, 5, 60),
(61, 12, 'http://example.com/images/default.png', '18kg 세탁기 내부', false, 6, 61),

-- 13. 4K UHD 스마트 TV 이미지
(62, 13, 'http://example.com/images/default.png', '43인치 TV 정면', false, 1, 62),
(63, 13, 'http://example.com/images/default.png', '55인치 TV 정면', true, 2, 63),
(64, 13, 'http://example.com/images/default.png', '65인치 TV 정면', false, 3, 64),
(65, 13, 'http://example.com/images/default.png', '75인치 TV 정면', false, 4, 65),
(66, 13, 'http://example.com/images/default.png', '기본형 TV 측면', false, 5, 66),
(67, 13, 'http://example.com/images/default.png', '벽걸이 TV 설치 예시', false, 6, 67),

-- 14. 초강력 무선 청소기 이미지
(68, 14, 'http://example.com/images/default.png', '메탈릭 실버 청소기 전체', true, 1, 68),
(69, 14, 'http://example.com/images/default.png', '레드 청소기 전체', false, 2, 69),
(70, 14, 'http://example.com/images/default.png', '블루 청소기 전체', false, 3, 70),
(71, 14, 'http://example.com/images/default.png', '기본형 배터리', false, 4, 71),
(72, 14, 'http://example.com/images/default.png', '대용량 배터리', false, 5, 72),

-- 15. 인버터 에어컨 이미지
(73, 15, 'http://example.com/images/default.png', '13평형 에어컨 전체', false, 1, 73),
(74, 15, 'http://example.com/images/default.png', '16평형 에어컨 전체', true, 2, 74),
(75, 15, 'http://example.com/images/default.png', '20평형 에어컨 전체', false, 3, 75),
(76, 15, 'http://example.com/images/default.png', '23평형 에어컨 전체', false, 4, 76),
(77, 15, 'http://example.com/images/default.png', '기본 설치 예시', false, 5, 77),
(78, 15, 'http://example.com/images/default.png', '프리미엄 설치 예시', false, 6, 78),

-- 16. 스팀 다리미 이미지
(79, 16, 'http://example.com/images/default.png', '블루 다리미 전체', true, 1, 79),
(80, 16, 'http://example.com/images/default.png', '화이트 다리미 전체', false, 2, 80),
(81, 16, 'http://example.com/images/default.png', '로즈 핑크 다리미 전체', false, 3, 81),

-- 17. 초고속 블렌더 이미지
(82, 17, 'http://example.com/images/default.png', '1.5L 블렌더 전체', false, 1, 82),
(83, 17, 'http://example.com/images/default.png', '2.0L 블렌더 전체', true, 2, 83),
(84, 17, 'http://example.com/images/default.png', '2.5L 블렌더 전체', false, 3, 84),
(85, 17, 'http://example.com/images/default.png', '메탈릭 실버 블렌더', false, 4, 85),
(86, 17, 'http://example.com/images/default.png', '매트 블랙 블렌더', false, 5, 86),
(87, 17, 'http://example.com/images/default.png', '레드 블렌더', false, 6, 87),

-- 18. 전자동 커피머신 이미지
(88, 18, 'http://example.com/images/default.png', '블랙 커피머신 전체', true, 1, 88),
(89, 18, 'http://example.com/images/default.png', '실버 커피머신 전체', false, 2, 89),
(90, 18, 'http://example.com/images/default.png', '프리미엄 골드 커피머신 전체', false, 3, 90),
(91, 18, 'http://example.com/images/default.png', '기본형 커피머신 측면', false, 4, 91),
(92, 18, 'http://example.com/images/default.png', '라떼아트 기능 예시', false, 5, 92),
(93, 18, 'http://example.com/images/default.png', '더블 보일러 상세', false, 6, 93),

-- 19. 스마트 전기밥솥 이미지
(94, 19, 'http://example.com/images/default.png', '6인용 밥솥 전체', false, 1, 94),
(95, 19, 'http://example.com/images/default.png', '10인용 밥솥 전체', true, 2, 95),
(96, 19, 'http://example.com/images/default.png', '15인용 밥솥 전체', false, 3, 96),
(97, 19, 'http://example.com/images/default.png', '화이트 밥솥 전체', false, 4, 97),
(98, 19, 'http://example.com/images/default.png', '블랙 밥솥 전체', false, 5, 98),
(99, 19, 'http://example.com/images/default.png', '로즈 골드 밥솥 전체', false, 6, 99),

-- 20. 공기청정기 이미지
(100, 20, 'http://example.com/images/default.png', '20평형 공기청정기 전체', false, 1, 100),
(101, 20, 'http://example.com/images/default.png', '30평형 공기청정기 전체', true, 2, 101),
(102, 20, 'http://example.com/images/default.png', '40평형 공기청정기 전체', false, 3, 102),
(103, 20, 'http://example.com/images/default.png', '화이트 공기청정기 측면', false, 4, 103),
(104, 20, 'http://example.com/images/default.png', '그레이 공기청정기 측면', false, 5, 104),
(105, 20, 'http://example.com/images/default.png', '블랙 공기청정기 측면', false, 6, 105),

-- 21. 방수 등산 텐트 이미지
(106, 21, 'http://example.com/images/default.png', '그린 등산 텐트', true, 1, 106),
(107, 21, 'http://example.com/images/default.png', '블루 등산 텐트', false, 2, 107),
(108, 21, 'http://example.com/images/default.png', '오렌지 등산 텐트', false, 3, 108),
(109, 21, 'http://example.com/images/default.png', '2인용 텐트 내부', false, 4, 109),
(110, 21, 'http://example.com/images/default.png', '3인용 텐트 내부', false, 5, 110),
(111, 21, 'http://example.com/images/default.png', '4인용 텐트 내부', false, 6, 111),

-- 22. 초경량 침낭 이미지
(112, 22, 'http://example.com/images/default.png', '네이비 침낭', true, 1, 112),
(113, 22, 'http://example.com/images/default.png', '블랙 침낭', false, 2, 113),
(114, 22, 'http://example.com/images/default.png', '레드 침낭', false, 3, 114),
(115, 22, 'http://example.com/images/default.png', '0°C 침낭 상세', false, 4, 115),
(116, 22, 'http://example.com/images/default.png', '-10°C 침낭 상세', false, 5, 116),
(117, 22, 'http://example.com/images/default.png', '-20°C 침낭 상세', false, 6, 117),

-- 23. 프로페셔널 등산화 이미지
(118, 23, 'http://example.com/images/default.png', '브라운 등산화', true, 1, 118),
(119, 23, 'http://example.com/images/default.png', '그레이 등산화', false, 2, 119),
(120, 23, 'http://example.com/images/default.png', '블랙 등산화', false, 3, 120),
(121, 23, 'http://example.com/images/default.png', '등산화 250mm 사이즈', false, 4, 121),
(122, 23, 'http://example.com/images/default.png', '등산화 260mm 사이즈', false, 5, 122),
(123, 23, 'http://example.com/images/default.png', '등산화 270mm 사이즈', false, 6, 123),
(124, 23, 'http://example.com/images/default.png', '등산화 280mm 사이즈', false, 7, 124),

-- 24. 전문가용 자전거 이미지
(125, 24, 'http://example.com/images/default.png', '매트 블랙 자전거', true, 1, 125),
(126, 24, 'http://example.com/images/default.png', '메탈릭 실버 자전거', false, 2, 126),
(127, 24, 'http://example.com/images/default.png', '레드 자전거', false, 3, 127),
(128, 24, 'http://example.com/images/default.png', 'S 사이즈 프레임', false, 4, 128),
(129, 24, 'http://example.com/images/default.png', 'M 사이즈 프레임', false, 5, 129),
(130, 24, 'http://example.com/images/default.png', 'L 사이즈 프레임', false, 6, 130),
(131, 24, 'http://example.com/images/default.png', 'XL 사이즈 프레임', false, 7, 131),

-- 25. 기능성 스포츠 웨어 세트 이미지
(132, 25, 'http://example.com/images/default.png', '블랙 스포츠웨어 세트', true, 1, 132),
(133, 25, 'http://example.com/images/default.png', '네이비 스포츠웨어 세트', false, 2, 133),
(134, 25, 'http://example.com/images/default.png', '그레이 스포츠웨어 세트', false, 3, 134),
(135, 25, 'http://example.com/images/default.png', 'S 사이즈 착용 모델', false, 4, 135),
(136, 25, 'http://example.com/images/default.png', 'M 사이즈 착용 모델', false, 5, 136),
(137, 25, 'http://example.com/images/default.png', 'L 사이즈 착용 모델', false, 6, 137),
(138, 25, 'http://example.com/images/default.png', 'XL 사이즈 착용 모델', false, 7, 138),

-- 26. 스마트 피트니스 워치 이미지
(139, 26, 'http://example.com/images/default.png', '블랙 스마트워치', true, 1, 139),
(140, 26, 'http://example.com/images/default.png', '실버 스마트워치', false, 2, 140),
(141, 26, 'http://example.com/images/default.png', '로즈 골드 스마트워치', false, 3, 141),
(142, 26, 'http://example.com/images/default.png', '실리콘 스트랩', false, 4, 142),
(143, 26, 'http://example.com/images/default.png', '나일론 스트랩', false, 5, 143),
(144, 26, 'http://example.com/images/default.png', '가죽 스트랩', false, 6, 144),

-- 27. 프리미엄 요가 매트 이미지
(145, 27, 'http://example.com/images/default.png', '퍼플 요가 매트', true, 1, 145),
(146, 27, 'http://example.com/images/default.png', '블루 요가 매트', false, 2, 146),
(147, 27, 'http://example.com/images/default.png', '핑크 요가 매트', false, 3, 147),
(148, 27, 'http://example.com/images/default.png', '그린 요가 매트', false, 4, 148),
(149, 27, 'http://example.com/images/default.png', '4mm 두께 매트', false, 5, 149),
(150, 27, 'http://example.com/images/default.png', '6mm 두께 매트', false, 6, 150),
(151, 27, 'http://example.com/images/default.png', '8mm 두께 매트', false, 7, 151),

-- 28. 접이식 캠핑 테이블 이미지
(152, 28, 'http://example.com/images/default.png', '소형 캠핑 테이블', false, 1, 152),
(153, 28, 'http://example.com/images/default.png', '중형 캠핑 테이블', true, 2, 153),
(154, 28, 'http://example.com/images/default.png', '대형 캠핑 테이블', false, 3, 154),
(155, 28, 'http://example.com/images/default.png', '실버 테이블', false, 4, 155),
(156, 28, 'http://example.com/images/default.png', '블랙 테이블', false, 5, 156),
(157, 28, 'http://example.com/images/default.png', '우드 패턴 테이블', false, 6, 157),

-- 29. 전문가용 수영 고글 이미지
(158, 29, 'http://example.com/images/default.png', '블랙 수영 고글', true, 1, 158),
(159, 29, 'http://example.com/images/default.png', '블루 수영 고글', false, 2, 159),
(160, 29, 'http://example.com/images/default.png', '클리어 수영 고글', false, 3, 160),
(161, 29, 'http://example.com/images/default.png', '미러 코팅 렌즈', false, 4, 161),
(162, 29, 'http://example.com/images/default.png', '클리어 렌즈', false, 5, 162),
(163, 29, 'http://example.com/images/default.png', '변색 렌즈', false, 6, 163),

-- 30. 홈트레이닝 덤벨 세트 이미지
(164, 30, 'http://example.com/images/default.png', '2.5-10kg 덤벨 세트', false, 1, 164),
(165, 30, 'http://example.com/images/default.png', '2.5-15kg 덤벨 세트', true, 2, 165),
(166, 30, 'http://example.com/images/default.png', '2.5-25kg 덤벨 세트', false, 3, 166),
(167, 30, 'http://example.com/images/default.png', '고무 그립 덤벨', false, 4, 167),
(168, 30, 'http://example.com/images/default.png', '크롬 그립 덤벨', false, 5, 168),
(169, 30, 'http://example.com/images/default.png', '논슬립 그립 덤벨', false, 6, 169);

-- 시퀀스 재설정
SELECT setval('product_images_id_seq', (SELECT MAX(id) FROM product_images));
