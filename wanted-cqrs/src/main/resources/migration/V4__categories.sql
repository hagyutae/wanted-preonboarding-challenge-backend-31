-- 대분류 (Level 1) 카테고리 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(1, '가구', 'furniture', '편안한 생활을 위한 다양한 가구 컬렉션', NULL, 1, 'https://example.com/categories/furniture.jpg'),
(2, '가전제품', 'electronics', '생활의 편리함을 더하는 가전제품 모음', NULL, 1, 'https://example.com/categories/electronics.jpg'),
(3, '인테리어 소품', 'interior-accessories', '공간의 분위기를 바꾸는 다양한 인테리어 소품', NULL, 1, 'https://example.com/categories/interior.jpg'),
(4, '주방용품', 'kitchen', '요리의 즐거움을 더하는 주방 용품', NULL, 1, 'https://example.com/categories/kitchen.jpg'),
(5, '침구 및 패브릭', 'bedding-fabrics', '편안한 수면과 인테리어를 위한 다양한 패브릭 제품', NULL, 1, 'https://example.com/categories/bedding.jpg'),
(6, '조명', 'lighting', '공간을 밝히는 다양한 조명 제품', NULL, 1, 'https://example.com/categories/lighting.jpg'),
(7, '패션', 'fashion', '트렌디한 패션 아이템 모음', NULL, 1, 'https://example.com/categories/fashion.jpg'),
(8, '아웃도어/스포츠', 'outdoor-sports', '액티브한 라이프스타일을 위한 아웃도어 및 스포츠 용품', NULL, 1, 'https://example.com/categories/outdoor-sports.jpg');

-- 가구 중분류 (Level 2) 카테고리 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(9, '거실 가구', 'living-room', '거실을 위한 다양한 가구 제품', 1, 2, 'https://example.com/categories/living-room.jpg'),
(10, '침실 가구', 'bedroom', '편안한 휴식을 위한 침실 가구', 1, 2, 'https://example.com/categories/bedroom.jpg'),
(11, '주방 가구', 'kitchen-furniture', '실용적이고 세련된 주방 가구', 1, 2, 'https://example.com/categories/kitchen-furniture.jpg'),
(12, '사무용 가구', 'office-furniture', '업무 효율을 높이는 사무용 가구', 1, 2, 'https://example.com/categories/office-furniture.jpg'),
(13, '아웃도어 가구', 'outdoor-furniture', '야외 공간을 위한 다양한 가구', 1, 2, 'https://example.com/categories/outdoor-furniture.jpg'),
(14, '수납 가구', 'storage-furniture', '공간 활용을 극대화하는 수납 가구', 1, 2, 'https://example.com/categories/storage-furniture.jpg');

-- 가전제품 중분류 (Level 2) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(15, '대형가전', 'major-appliances', '생활의 편리함을 더하는 대형 가전제품', 2, 2, 'https://example.com/categories/major-appliances.jpg'),
(16, '소형가전', 'small-appliances', '실용적인 소형 가전제품', 2, 2, 'https://example.com/categories/small-appliances.jpg'),
(17, '주방가전', 'kitchen-appliances', '요리의 즐거움을 더하는 주방 가전', 2, 2, 'https://example.com/categories/kitchen-appliances.jpg'),
(18, '음향 및 영상기기', 'audio-video', '고품질 음향 및 영상 기기', 2, 2, 'https://example.com/categories/audio-video.jpg');

-- 인테리어 소품 중분류 (Level 2) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(19, '장식품', 'decorations', '공간을 돋보이게 하는 다양한 장식품', 3, 2, 'https://example.com/categories/decorations.jpg'),
(20, '벽장식', 'wall-decor', '벽면을 아름답게 꾸미는 다양한 소품', 3, 2, 'https://example.com/categories/wall-decor.jpg'),
(21, '화병/화분', 'vases-planters', '식물과 꽃을 위한 화병 및 화분', 3, 2, 'https://example.com/categories/vases-planters.jpg'),
(22, '쿠션/방석', 'cushions-pillows', '편안함을 더하는 다양한 쿠션과 방석', 3, 2, 'https://example.com/categories/cushions-pillows.jpg');

-- 주방용품 중분류 (Level 2) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(23, '조리도구', 'cooking-tools', '요리를 위한 다양한 조리도구', 4, 2, 'https://example.com/categories/cooking-tools.jpg'),
(24, '식기/그릇', 'tableware', '테이블을 아름답게 꾸미는 다양한 식기', 4, 2, 'https://example.com/categories/tableware.jpg'),
(25, '보관용기', 'storage-containers', '음식 보관을 위한 다양한 용기', 4, 2, 'https://example.com/categories/storage-containers.jpg'),
(26, '주방 소품', 'kitchen-accessories', '주방을 더욱 편리하게 만드는 소품', 4, 2, 'https://example.com/categories/kitchen-accessories.jpg');

-- 침구 및 패브릭 중분류 (Level 2) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(27, '이불/침구세트', 'bedding-sets', '편안한 수면을 위한 침구세트', 5, 2, 'https://example.com/categories/bedding-sets.jpg'),
(28, '베개/매트리스', 'pillows-mattresses', '최적의 수면 환경을 위한 베개와 매트리스', 5, 2, 'https://example.com/categories/pillows-mattresses.jpg'),
(29, '커튼/블라인드', 'curtains-blinds', '다양한 스타일의 커튼과 블라인드', 5, 2, 'https://example.com/categories/curtains-blinds.jpg'),
(30, '러그/카펫', 'rugs-carpets', '공간에 따뜻함을 더하는 러그와 카펫', 5, 2, 'https://example.com/categories/rugs-carpets.jpg');

-- 조명 중분류 (Level 2) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(31, '천장 조명', 'ceiling-lights', '다양한 스타일의 천장 조명', 6, 2, 'https://example.com/categories/ceiling-lights.jpg'),
(32, '테이블 램프', 'table-lamps', '공간에 분위기를 더하는 테이블 램프', 6, 2, 'https://example.com/categories/table-lamps.jpg'),
(33, '벽 조명', 'wall-lights', '벽면을 아름답게 비추는 벽 조명', 6, 2, 'https://example.com/categories/wall-lights.jpg'),
(34, '실외 조명', 'outdoor-lighting', '야외 공간을 위한 다양한 조명', 6, 2, 'https://example.com/categories/outdoor-lighting.jpg');

-- 패션 중분류 (Level 2) 카테고리 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(35, '여성 의류', 'womens-clothing', '다양한 스타일의 여성 의류', 7, 2, 'https://example.com/categories/womens-clothing.jpg'),
(36, '남성 의류', 'mens-clothing', '다양한 스타일의 남성 의류', 7, 2, 'https://example.com/categories/mens-clothing.jpg'),
(37, '아동 의류', 'kids-clothing', '편안하고 귀여운 아동 의류', 7, 2, 'https://example.com/categories/kids-clothing.jpg'),
(38, '패션 액세서리', 'fashion-accessories', '스타일을 완성하는 패션 액세서리', 7, 2, 'https://example.com/categories/fashion-accessories.jpg'),
(39, '신발', 'shoes', '다양한 스타일과 용도의 신발', 7, 2, 'https://example.com/categories/shoes.jpg'),
(40, '가방', 'bags', '실용적이고 스타일리시한 가방', 7, 2, 'https://example.com/categories/bags.jpg');

-- 아웃도어/스포츠 중분류 (Level 2) 카테고리 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(41, '캠핑 용품', 'camping', '즐거운 캠핑을 위한 다양한 용품', 8, 2, 'https://example.com/categories/camping.jpg'),
(42, '스포츠 장비', 'sports-equipment', '다양한 스포츠를 위한 장비', 8, 2, 'https://example.com/categories/sports-equipment.jpg'),
(43, '아웃도어 의류', 'outdoor-clothing', '기능성과 스타일을 갖춘 아웃도어 의류', 8, 2, 'https://example.com/categories/outdoor-clothing.jpg'),
(44, '피트니스', 'fitness', '건강한 라이프스타일을 위한 피트니스 용품', 8, 2, 'https://example.com/categories/fitness.jpg');

-- 가구 > 거실 가구 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(45, '소파', 'sofa', '다양한 스타일과 크기의 소파', 9, 3, 'https://example.com/categories/sofa.jpg'),
(46, '거실장/TV장', 'tv-stands', '다양한 스타일의 TV 스탠드 및 거실장', 9, 3, 'https://example.com/categories/tv-stands.jpg'),
(47, '테이블/좌탁', 'tables', '다양한 용도의 테이블', 9, 3, 'https://example.com/categories/tables.jpg'),
(48, '의자/스툴', 'chairs-stools', '다양한 스타일의 의자와 스툴', 9, 3, 'https://example.com/categories/chairs-stools.jpg');

-- 가구 > 침실 가구 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(49, '침대', 'beds', '편안한 수면을 위한 다양한 침대', 10, 3, 'https://example.com/categories/beds.jpg'),
(50, '매트리스', 'mattresses', '최적의 수면을 위한 매트리스', 10, 3, 'https://example.com/categories/mattresses.jpg'),
(51, '옷장/붙박이장', 'wardrobes', '다양한 크기와 스타일의 옷장', 10, 3, 'https://example.com/categories/wardrobes.jpg'),
(52, '화장대/서랍장', 'dressers', '실용적인 화장대와 서랍장', 10, 3, 'https://example.com/categories/dressers.jpg');

-- 가구 > 주방 가구 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(53, '식탁/의자세트', 'dining-sets', '다양한 스타일의 식탁 세트', 11, 3, 'https://example.com/categories/dining-sets.jpg'),
(54, '주방수납장', 'kitchen-cabinets', '주방 공간을 활용하는 수납장', 11, 3, 'https://example.com/categories/kitchen-cabinets.jpg'),
(55, '아일랜드/카트', 'kitchen-islands', '주방 기능성을 높이는 아일랜드와 카트', 11, 3, 'https://example.com/categories/kitchen-islands.jpg');

-- 가구 > 사무용 가구 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(56, '책상', 'desks', '다양한 스타일과 크기의 책상', 12, 3, 'https://example.com/categories/desks.jpg'),
(57, '사무용 의자', 'office-chairs', '편안한 업무를 위한 사무용 의자', 12, 3, 'https://example.com/categories/office-chairs.jpg'),
(58, '책장/책꽂이', 'bookshelves', '책과 소품을 위한 다양한 수납 가구', 12, 3, 'https://example.com/categories/bookshelves.jpg'),
(59, '파일링 캐비닛', 'filing-cabinets', '문서 정리를 위한 캐비닛', 12, 3, 'https://example.com/categories/filing-cabinets.jpg');

-- 가전제품 > 대형가전 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(60, '냉장고', 'refrigerators', '다양한 크기와 기능의 냉장고', 15, 3, 'https://example.com/categories/refrigerators.jpg'),
(61, '세탁기/건조기', 'washers-dryers', '효율적인 세탁과 건조를 위한 가전', 15, 3, 'https://example.com/categories/washers-dryers.jpg'),
(62, '에어컨', 'air-conditioners', '쾌적한 실내 환경을 위한 에어컨', 15, 3, 'https://example.com/categories/air-conditioners.jpg'),
(63, 'TV', 'televisions', '고화질 영상을 위한 다양한 TV', 15, 3, 'https://example.com/categories/televisions.jpg');

-- 가전제품 > 소형가전 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(64, '청소기', 'vacuum-cleaners', '효율적인 청소를 위한 다양한 청소기', 16, 3, 'https://example.com/categories/vacuum-cleaners.jpg'),
(65, '다리미/스팀다리미', 'irons', '의류 관리를 위한 다리미', 16, 3, 'https://example.com/categories/irons.jpg'),
(66, '선풍기/서큘레이터', 'fans', '시원한 바람을 위한 선풍기와 서큘레이터', 16, 3, 'https://example.com/categories/fans.jpg');

-- 패션 > 여성 의류 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(67, '원피스', 'dresses', '다양한 스타일의 원피스', 35, 3, 'https://example.com/categories/dresses.jpg'),
(68, '블라우스/셔츠', 'blouses-shirts', '다양한 디자인의 블라우스와 셔츠', 35, 3, 'https://example.com/categories/blouses-shirts.jpg'),
(69, '티셔츠', 't-shirts-women', '편안한 착용감의 여성 티셔츠', 35, 3, 'https://example.com/categories/t-shirts-women.jpg'),
(70, '바지/데님', 'pants-jeans-women', '다양한 스타일의 바지와 데님', 35, 3, 'https://example.com/categories/pants-jeans-women.jpg'),
(71, '스커트', 'skirts', '다양한 길이와 스타일의 스커트', 35, 3, 'https://example.com/categories/skirts.jpg');

-- 패션 > 남성 의류 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(72, '셔츠', 'shirts-men', '다양한 스타일의 남성 셔츠', 36, 3, 'https://example.com/categories/shirts-men.jpg'),
(73, '티셔츠/맨투맨', 't-shirts-sweatshirts-men', '편안한 착용감의 티셔츠와 맨투맨', 36, 3, 'https://example.com/categories/t-shirts-sweatshirts-men.jpg'),
(74, '바지/데님', 'pants-jeans-men', '다양한 스타일의 바지와 데님', 36, 3, 'https://example.com/categories/pants-jeans-men.jpg'),
(75, '정장/수트', 'suits', '격식있는 모임을 위한 정장과 수트', 36, 3, 'https://example.com/categories/suits.jpg');

-- 아웃도어/스포츠 > 캠핑 용품 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(76, '텐트/타프', 'tents-tarps', '다양한 크기와 용도의 텐트와 타프', 41, 3, 'https://example.com/categories/tents-tarps.jpg'),
(77, '침낭/매트', 'sleeping-bags-mats', '편안한 수면을 위한 침낭과 매트', 41, 3, 'https://example.com/categories/sleeping-bags-mats.jpg'),
(78, '취사용품', 'cooking-gear', '캠핑용 취사 도구', 41, 3, 'https://example.com/categories/cooking-gear.jpg'),
(79, '캠핑 가구', 'camping-furniture', '접이식 의자와 테이블 등 캠핑용 가구', 41, 3, 'https://example.com/categories/camping-furniture.jpg');

-- 아웃도어/스포츠 > 스포츠 장비 소분류 (Level 3) 삽입
INSERT INTO categories (id, name, slug, description, parent_id, level, image_url) VALUES
(80, '자전거', 'bicycles', '다양한 용도의 자전거', 42, 3, 'https://example.com/categories/bicycles.jpg'),
(81, '등산 장비', 'hiking-gear', '안전한 등산을 위한 장비', 42, 3, 'https://example.com/categories/hiking-gear.jpg'),
(82, '수영 용품', 'swimming-gear', '수영을 위한 다양한 용품', 42, 3, 'https://example.com/categories/swimming-gear.jpg'),
(83, '헬스 기구', 'fitness-equipment', '홈트레이닝을 위한 헬스 기구', 42, 3, 'https://example.com/categories/fitness-equipment.jpg');

-- 시퀀스 재설정
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
