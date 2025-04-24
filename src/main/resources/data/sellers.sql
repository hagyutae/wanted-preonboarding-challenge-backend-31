-- Insert Sellers with explicit IDs
INSERT INTO sellers (id, name, description, logo_url, rating, contact_email, contact_phone, created_at) VALUES
-- Home & Furniture Sellers
(1, '홈퍼니처', '최고의 가구 전문 판매점', 'https://example.com/logos/homefurniture.png', 4.8, 'contact@homefurniture.com', '02-1234-5678', NOW()),
(2, '모던리빙', '트렌디한 현대 가구 전문 매장', 'https://example.com/logos/modernliving.png', 4.6, 'info@modernliving.co.kr', '02-7890-1234', NOW()),
(3, '클래식인테리어', '클래식하고 고급스러운 인테리어 솔루션', 'https://example.com/logos/classicinterior.png', 4.7, 'sales@classicinterior.com', '032-456-7890', NOW()),
(4, '에코홈', '친환경 가구 전문 판매점', 'https://example.com/logos/ecohome.png', 4.5, 'eco@ecohome.kr', '02-2345-6789', NOW()),
(5, '스마트리빙', '스마트 가구 혁신 기업', 'https://example.com/logos/smartliving.png', 4.4, 'support@smartliving.com', '031-789-0123', NOW()),
(6, '럭셔리홈', '고급 인테리어 전문 매장', 'https://example.com/logos/luxuryhome.png', 4.9, 'luxury@home.kr', '02-9012-3456', NOW()),
(7, '미니멀리빙', '미니멀 라이프스타일 가구 브랜드', 'https://example.com/logos/minimalliving.png', 4.7, 'minimal@living.com', '031-234-5678', NOW()),
(8, '빈티지코너', '빈티지 & 앤티크 가구 전문', 'https://example.com/logos/vintagecorner.png', 4.6, 'vintage@corner.kr', '032-345-6789', NOW()),
(9, '디자인랩', '혁신적인 가구 디자인 매장', 'https://example.com/logos/designlab.png', 4.5, 'design@lab.com', '02-4567-8901', NOW()),
(10, '러스틱홈', '자연 친화적 러스틱 가구 전문', 'https://example.com/logos/rustichome.png', 4.4, 'rustic@home.kr', '031-567-8901', NOW()),

-- Electronics & Home Appliances Sellers
(11, '디지털프론티어', '최첨단 전자기기 전문점', 'https://example.com/logos/digitalfrontier.png', 4.8, 'digital@frontier.com', '02-5678-9012', NOW()),
(12, '테크엑스퍼트', '전문가용 첨단 기기 매장', 'https://example.com/logos/techexpert.png', 4.7, 'tech@expert.kr', '032-678-9012', NOW()),
(13, '이노베이티브가젯', '혁신적인 전자제품 큐레이션', 'https://example.com/logos/innovativegadgets.png', 4.6, 'gadgets@innovative.com', '031-789-0123', NOW()),
(14, '스마트존', '스마트 디바이스 전문 매장', 'https://example.com/logos/smartzone.png', 4.5, 'smart@zone.kr', '02-6789-0123', NOW()),
(15, '하이테크마트', '최신 전자제품 원스톱 쇼핑', 'https://example.com/logos/hightechmart.png', 4.4, 'hightech@mart.com', '031-890-1234', NOW()),
(16, '테크몰', '최신 전자제품 및 가전 전문 판매점', 'https://example.com/logos/techmall.png', 4.6, 'customer@techmall.kr', '02-3456-7890', NOW()),
(17, '스마트테크', '혁신적인 전자기기 매장', 'https://example.com/logos/smarttech.png', 4.5, 'info@smarttech.com', '031-234-5678', NOW()),
(18, '홈테크', '스마트홈 솔루션 전문', 'https://example.com/logos/hometech.png', 4.3, 'sales@hometech.co.kr', '032-567-8901', NOW()),
(19, '키친테크', '주방가전 전문 매장', 'https://example.com/logos/kitchentech.png', 4.7, 'kitchen@tech.kr', '02-7890-1234', NOW()),
(20, '스마트홈솔루션', '통합 스마트홈 가전 전문', 'https://example.com/logos/smarthomesolution.png', 4.6, 'solution@smarthome.com', '031-901-2345', NOW()),
(21, '클린테크홈', '첨단 청소가전 전문점', 'https://example.com/logos/cleantechhome.png', 4.5, 'clean@techome.kr', '032-012-3456', NOW()),
(22, '홈케어센터', '가전제품 토탈 케어 서비스', 'https://example.com/logos/homecare.png', 4.4, 'care@home.com', '02-8901-2345', NOW()),
(23, '에너지솔루션', '에너지 효율 가전 전문', 'https://example.com/logos/energysolution.png', 4.3, 'energy@solution.kr', '031-123-4567', NOW()),

-- Fashion & Lifestyle Sellers
(24, '패션하우스', '트렌디한 라이프스타일 스토어', 'https://example.com/logos/fashionhouse.png', 4.7, 'contact@fashionhouse.kr', '02-8901-2345', NOW()),
(25, '라이프스타일스토어', '다양한 라이프스타일 제품 전문', 'https://example.com/logos/lifestylestore.png', 4.6, 'support@lifestylestore.com', '031-345-6789', NOW()),
(26, '트렌드메이커스', '글로벌 트렌드 라이프스타일 매장', 'https://example.com/logos/trendmakers.png', 4.8, 'trend@makers.com', '02-9012-3456', NOW()),
(27, '라이프스타일랩', '혁신적인 라이프스타일 제품 큐레이션', 'https://example.com/logos/lifestylelab.png', 4.7, 'lab@lifestyle.kr', '031-234-5678', NOW()),
(28, '소셜트렌드', '소셜미디어 트렌드 제품 전문', 'https://example.com/logos/socialtrend.png', 4.6, 'social@trend.com', '032-345-6789', NOW()),
(29, '글로벌스타일', '글로벌 브랜드 라이프스타일 매장', 'https://example.com/logos/globalstyle.png', 4.5, 'global@style.kr', '02-4567-8901', NOW()),
(30, '마이너리티', '독특한 라이프스타일 브랜드', 'https://example.com/logos/minority.png', 4.4, 'contact@minority.com', '031-567-8901', NOW()),

-- Outdoor & Sports Sellers
(31, '아웃도어프로', '전문 아웃도어 용품 매장', 'https://example.com/logos/outdoorpro.png', 4.7, 'outdoor@pro.kr', '02-5678-9012', NOW()),
(32, '스포츠존', '종합 스포츠 용품 전문점', 'https://example.com/logos/sportszone.png', 4.6, 'sports@zone.com', '032-678-9012', NOW()),
(33, '트레킹마스터', '트레킹 & 캠핑 전문 매장', 'https://example.com/logos/trekingmaster.png', 4.5, 'trekking@master.kr', '031-789-0123', NOW()),
(34, '피트니스허브', '피트니스 용품 전문점', 'https://example.com/logos/fitnesshub.png', 4.4, 'fitness@hub.com', '02-6789-0123', NOW()),
(35, '익스트림스포츠', '익스트림 스포츠 용품 전문', 'https://example.com/logos/extremesports.png', 4.3, 'extreme@sports.kr', '031-890-1234', NOW()),

-- Kitchen & Cooking Sellers
(36, '쿠킹마스터', '주방용품 전문 매장', 'https://example.com/logos/cookingmaster.png', 4.8, 'cooking@master.kr', '02-7890-1234', NOW()),
(37, '쉐프스토어', '전문 요리사를 위한 키친 용품', 'https://example.com/logos/chefstore.png', 4.7, 'chef@store.com', '031-901-2345', NOW()),
(38, '홈쿠킹', '홈쿠킹 필수 용품 전문점', 'https://example.com/logos/homecooking.png', 4.6, 'home@cooking.kr', '032-012-3456', NOW()),
(39, '키친웨어하우스', '다양한 주방용품 대형 매장', 'https://example.com/logos/kitchenwarehouse.png', 4.5, 'kitchen@warehouse.com', '02-8901-2345', NOW()),
(40, '쿠킹이노베이션', '혁신적인 주방용품 매장', 'https://example.com/logos/cookinginnovation.png', 4.4, 'innovation@cooking.kr', '031-123-4567', NOW()),

-- Eco-Friendly & Sustainable Sellers
(41, '그린라이프', '친환경 라이프스타일 매장', 'https://example.com/logos/greenlife.png', 4.7, 'green@life.kr', '02-9012-3456', NOW()),
(42, '에코프렌들리', '지속 가능한 제품 전문점', 'https://example.com/logos/ecofriendly.png', 4.6, 'eco@friendly.com', '031-234-5678', NOW()),
(43, '서스테이너블', '지속 가능성 중심 매장', 'https://example.com/logos/sustainable.png', 4.5, 'sustainable@store.kr', '032-345-6789', NOW()),
(44, '제로웨이스트', '제로 웨이스트 라이프스타일 숍', 'https://example.com/logos/zerowaste.png', 4.4, 'zero@waste.com', '02-4567-8901', NOW()),
(45, '플라넷러브', '지구를 생각하는 라이프스타일', 'https://example.com/logos/planetlove.png', 4.3, 'planet@love.kr', '031-567-8901', NOW()),

-- Additional Sellers
(46, '프리미엄샵', '최고급 제품 전문 판매점', 'https://example.com/logos/premiumshop.png', 4.9, 'premium@shop.kr', '02-4567-8901', NOW()),
(47, '디자인워크', '독특한 디자인의 제품 전문', 'https://example.com/logos/designwork.png', 4.5, 'design@work.com', '032-678-9012', NOW()),
(48, '트렌드메이커', '최신 트렌드 제품 큐레이션', 'https://example.com/logos/trendmaker.png', 4.4, 'trend@maker.kr', '031-567-8901', NOW()),
(49, '퀄리티스토어', '고품질 제품 전문 매장', 'https://example.com/logos/qualitystore.png', 4.7, 'quality@store.com', '02-5678-9012', NOW()),
(50, '이노베이션', '혁신적인 제품 전문 판매', 'https://example.com/logos/innovation.png', 4.6, 'innovation@store.kr', '031-789-0123', NOW());

-- 시퀀스 재설정
SELECT setval('sellers_id_seq', (SELECT MAX(id) FROM sellers));
