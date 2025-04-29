# 데이터

- 총 30개의 상품 데이터 (가구, 가전제품, 아웃도어/스포츠 등)
- 50개의 판매자와 84개의 브랜드
- 3단계 계층 구조로 구성된 83개의 카테고리
- 100개의 태그
- 10명의 사용자와 100개의 상품 리뷰
- 상품별 다양한 옵션 그룹, 옵션 및 이미지

---

# API

### 상품 관리 API

- POST /api/products: 상품 등록 (관련 정보 모두 포함)
- GET /api/products: 상품 목록 조회 (핕터 조건 적용, 정렬, 페이지네이션)
    - 검색 기능 포함
    - 필터 항목: 태그, 등록일, 판매자, 브랜드, 가격 범위, 카테고리, 재고 유무
- GET /api/products/{id}: 상품 상세 조회 (모든 관련 정보 포함)
- PUT /api/products/{id}: 상품 수정
- DELETE /api/products/{id}: 상품 삭제
- POST /api/products/{id}/options: 상품 옵션 추가
- PUT /api/products/{id}/options/{optionId}: 상품 옵션 수정
- DELETE /api/products/{id}/options/{optionId}: 상품 옵션 삭제
- POST /api/products/{id}/images: 상품 이미지 추가

### 카테고리 API

- GET /api/categories: 카테고리 목록 조회 (계층 구조 포함)
- GET /api/categories/{id}/products: 특정 카테고리의 상품 목록 조회

### 메인 페이지 API

- GET /api/main: 메인 페이지용 상품 목록 (신규 상품 + 카테고리 별 인기 상품 순)

### 리뷰 API

- GET /api/products/{id}/reviews: 상품 리뷰 조회
- POST /api/products/{id}/reviews: 리뷰 작성
- PUT /api/reviews/{id}: 리뷰 수정
- DELETE /api/reviews/{id}: 리뷰 삭제
