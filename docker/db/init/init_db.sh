#!/bin/bash
set -e

echo "▶ 1. 스키마 생성"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/ddl.sql

echo "▶ 2. 초기 데이터 삽입"

echo "▶▶ 판매자 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/sellers.sql

echo "▶▶ 브랜드 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/brands.sql

echo "▶▶ 카테고리 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/categories.sql

echo "▶▶ 태그 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/tags.sql

echo "▶▶ 기본 상품 정보 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/products.sql

echo "▶▶ 상품 옵션 그룹, 옵션, 이미지 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/product_options.sql

echo "▶▶ 상품 상세정보, 가격, 카테고리/태그 매핑 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/product_extended.sql

echo "▶▶ 사용자 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/users.sql

echo "▶▶ 상품 리뷰 데이터 삽입"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -f /docker-entrypoint-initdb.d/seed/reviews.sql

echo "✅ 초기화 완료!"