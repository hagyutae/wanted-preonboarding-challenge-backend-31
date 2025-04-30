import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import {
  ProductCategoryRepository,
  ProductDetailRepository,
  ProductImageRepository,
  ProductOptionGroupRepository,
  ProductPriceRepository,
  ProductRepository,
  ProductTagRepository,
} from "src/infrastructure/repositories";
import { FilterDTO, ProductInputDTO } from "../dto";

@Injectable()
export default class ProductService {
  constructor(
    private readonly entity_manager: EntityManager,
    private readonly repository: ProductRepository,
  ) {}

  async register({
    detail,
    price,
    categories,
    option_groups,
    images,
    tags: tag_ids,
    seller_id,
    brand_id,
    ...product
  }: ProductInputDTO) {
    try {
      // 상품 등록 트랜잭션 처리
      const product_entity = await this.entity_manager.transaction(async (manager) => {
        // 상품 등록
        const product_entity = await new ProductRepository(manager).save({
          product,
          seller_id,
          brand_id,
        });

        // 상품 상세 등록
        await new ProductDetailRepository(manager).save(detail, product_entity.id);

        // 상품 가격 등록
        await new ProductPriceRepository(manager).save(price, product_entity.id);

        // 상품 카테고리 등록
        await new ProductCategoryRepository(manager).save(categories, product_entity.id);

        // 상품 옵션 등록
        await new ProductOptionGroupRepository(manager).save(option_groups, product_entity.id);

        // 상품 이미지 등록
        await new ProductImageRepository(manager).saves(images, product_entity.id);

        // 상품 태그 등록
        await new ProductTagRepository(manager).save(tag_ids, product_entity.id);

        return product_entity;
      });
      // 상품 등록 결과 반환
      return (({ id, name, slug, created_at, updated_at }) => ({
        id,
        name,
        slug,
        created_at,
        updated_at,
      }))(product_entity);
    } catch (error) {
      throw new Error((error as Error).message);
    }
  }

  async find_all({ page = 1, per_page = 10, sort, ...rest }: FilterDTO) {
    const [sort_field, sort_order] = sort?.split(":") ?? ["created_at", "DESC"];

    const items = await this.repository.find_by_filters({
      page,
      per_page,
      sort_field,
      sort_order,
      ...rest,
    });

    // 페이지네이션 요약 정보
    const pagination = {
      total_items: items.length,
      total_pages: Math.ceil(items.length / (per_page ?? 10)),
      current_page: page ?? 1,
      per_page: per_page ?? 10,
    };

    return { items, pagination };
  }

  async find(id: number) {
    return this.repository.get_by_id(id);
  }

  async edit(
    id: number,
    { detail, seller_id, brand_id, price, categories, ...product }: ProductInputDTO,
  ) {
    try {
      const updated_product_entity = await this.entity_manager.transaction(async (manager) => {
        // 상품 디테일 업데이트
        await new ProductDetailRepository(manager).update(detail, id);

        // 상품 가격 업데이트
        await new ProductPriceRepository(manager).update(price, id);

        // 상품 카테고리 업데이트
        await new ProductCategoryRepository(manager).update(categories, id);

        // 상품 제품 업데이트
        const updated_product_entity = await new ProductRepository(manager).update(id, {
          seller_id,
          brand_id,
          product,
        });

        return updated_product_entity;
      });
      return (({ id, name, slug, updated_at }) => ({
        id,
        name,
        slug,
        updated_at,
      }))(updated_product_entity);
    } catch (error) {
      throw new Error((error as Error).message);
    }
  }

  async remove(id: number) {
    return this.repository.delete(id);
  }
}
