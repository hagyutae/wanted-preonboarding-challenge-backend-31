-- 태그(Tag) 데이터 삽입 (id 값 지정)
INSERT INTO tags (id, name, slug) VALUES
-- 가구 카테고리
(1, '소파', 'sofa'),
(2, '침대', 'bed'),
(3, '테이블', 'table'),
(4, '책상', 'desk'),
(5, '의자', 'chair'),
(6, '수납장', 'storage'),
(7, '서랍장', 'drawer'),
(8, '책장', 'bookshelf'),
(9, '옷장', 'wardrobe'),
(10, '식탁', 'dining-table'),

-- 가전 카테고리
(11, '냉장고', 'refrigerator'),
(12, '세탁기', 'washing-machine'),
(13, 'TV', 'television'),
(14, '에어컨', 'air-conditioner'),
(15, '청소기', 'vacuum-cleaner'),
(16, '전자레인지', 'microwave'),
(17, '인덕션', 'induction'),
(18, '커피머신', 'coffee-machine'),
(19, '블렌더', 'blender'),
(20, '토스터', 'toaster'),

-- 패션 카테고리
(21, '티셔츠', 't-shirt'),
(22, '청바지', 'jeans'),
(23, '재킷', 'jacket'),
(24, '원피스', 'dress'),
(25, '코트', 'coat'),
(26, '니트', 'knitwear'),
(27, '운동화', 'sneakers'),
(28, '구두', 'shoes'),
(29, '가방', 'bag'),
(30, '악세서리', 'accessories'),

-- 뷰티 카테고리
(31, '스킨케어', 'skincare'),
(32, '메이크업', 'makeup'),
(33, '향수', 'perfume'),
(34, '헤어케어', 'haircare'),
(35, '바디케어', 'bodycare'),
(36, '네일', 'nail'),
(37, '마스크팩', 'mask-pack'),
(38, '선크림', 'sunscreen'),
(39, '클렌징', 'cleansing'),
(40, '립스틱', 'lipstick'),

-- 식품 카테고리
(41, '건강식품', 'health-food'),
(42, '간편식', 'convenience-food'),
(43, '베이커리', 'bakery'),
(44, '과일', 'fruits'),
(45, '채소', 'vegetables'),
(46, '육류', 'meat'),
(47, '수산물', 'seafood'),
(48, '음료', 'beverages'),
(49, '과자', 'snacks'),
(50, '유제품', 'dairy'),

-- 디지털/IT
(51, '스마트폰', 'smartphone'),
(52, '노트북', 'laptop'),
(53, '태블릿', 'tablet'),
(54, '이어폰', 'earphones'),
(55, '스마트워치', 'smartwatch'),
(56, '게임기', 'game-console'),
(57, '카메라', 'camera'),
(58, '헤드폰', 'headphones'),
(59, '블루투스', 'bluetooth'),
(60, '충전기', 'charger'),

-- 홈인테리어
(61, '조명', 'lighting'),
(62, '쿠션', 'cushion'),
(63, '러그', 'rug'),
(64, '커튼', 'curtain'),
(65, '거울', 'mirror'),
(66, '액자', 'frame'),
(67, '벽지', 'wallpaper'),
(68, '화분', 'flowerpot'),
(69, '인테리어소품', 'home-decor'),
(70, '캔들', 'candle'),

-- 스포츠/레저
(71, '운동복', 'sportswear'),
(72, '요가매트', 'yoga-mat'),
(73, '텐트', 'tent'),
(74, '자전거', 'bicycle'),
(75, '수영용품', 'swimming-gear'),
(76, '등산용품', 'hiking-gear'),
(77, '골프용품', 'golf-equipment'),
(78, '캠핑용품', 'camping-gear'),
(79, '낚시용품', 'fishing-gear'),
(80, '헬스기구', 'fitness-equipment'),

-- 특성 및 트렌드
(81, '베스트셀러', 'bestseller'),
(82, '신상품', 'new-arrival'),
(83, '한정판', 'limited-edition'),
(84, '친환경', 'eco-friendly'),
(85, '프리미엄', 'premium'),
(86, '할인', 'discount'),
(87, '세일', 'sale'),
(88, '히트상품', 'hot-item'),
(89, '시즌특가', 'seasonal-deal'),
(90, '단독특가', 'exclusive-price'),

-- 라이프스타일
(91, '홈오피스', 'home-office'),
(92, '키친가전', 'kitchen-appliance'),
(93, '스마트홈', 'smart-home'),
(94, '반려동물', 'pet-supplies'),
(95, '육아용품', 'baby-products'),
(96, '문구용품', 'stationery'),
(97, '취미용품', 'hobby-supplies'),
(98, '여행용품', 'travel-gear'),
(99, '계절용품', 'seasonal-item'),
(100, '파티용품', 'party-supplies');

-- 시퀀스 재설정
SELECT setval('tags_id_seq', (SELECT MAX(id) FROM tags));
