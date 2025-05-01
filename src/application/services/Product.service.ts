import { Inject, Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import {
  Product,
  Product_Catalog,
  Product_Category,
  Product_Detail,
  Product_Image,
  Product_Option_Group,
  Product_Price,
  Product_Summary,
  Product_Tag,
} from "src/domain/entities";
import IRepository from "src/domain/repositories/IRepository";
import { ProductRepository } from "src/infrastructure/repositories";
import { FilterDTO, ProductInputDTO } from "../dto";

@Injectable()
export default class ProductService {
  constructor(
    private readonly entity_manager: EntityManager,
    @Inject("IProductRepository")
    private readonly repository: IRepository<Product | Product_Summary | Product_Catalog>,
    @Inject("IProductDetailRepository")
    private readonly product_detail_repository: IRepository<Product_Detail>,
    @Inject("IProductPriceRepository")
    private readonly product_price_repository: IRepository<Product_Price>,
    @Inject("IProductCategoryRepository")
    private readonly product_category_repository: IRepository<Product_Category>,
    @Inject("IProductOptionGroupRepository")
    private readonly product_option_group_repository: IRepository<Product_Option_Group>,
    @Inject("IProductImageRepository")
    private readonly product_image_repository: IRepository<Product_Image>,
    @Inject("IProductTagRepository")
    private readonly product_tag_repository: IRepository<Product_Tag>,
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
          ...product,
          seller_id,
          brand_id,
        });
        const { id: product_id } = product_entity;

        // 상품 상세 등록
        await this.product_detail_repository.with_transaction(manager).save({
          ...detail,
          product_id,
        });

        // 상품 가격 등록
        await this.product_price_repository.with_transaction(manager).save({
          ...price,
          product_id,
        });

        // 상품 카테고리 등록
        await this.product_category_repository
          .with_transaction(manager)
          .saves(categories.map((category) => ({ ...category, product_id })));

        // 상품 옵션 등록
        await this.product_option_group_repository
          .with_transaction(manager)
          .saves(option_groups.map((group) => ({ ...group, product_id })));

        // 상품 이미지 등록
        await this.product_image_repository
          .with_transaction(manager)
          .saves(images.map((image) => ({ ...image, product_id })));

        // 상품 태그 등록
        await this.product_tag_repository
          .with_transaction(manager)
          .saves(tag_ids.map((tag_id) => ({ tag_id, product_id })));

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
    return this.repository.find_by_id(id);
  }

  async edit(
    product_id: number,
    { detail, seller_id, brand_id, price, categories, ...product }: ProductInputDTO,
  ) {
    try {
      const updated_product_entity = await this.entity_manager.transaction(async (manager) => {
        // 상품 디테일 업데이트
        await this.product_detail_repository
          .with_transaction(manager)
          .update({ ...detail, product_id });

        // 상품 가격 업데이트
        await this.product_price_repository
          .with_transaction(manager)
          .update({ ...price, product_id });

        // 상품 카테고리 업데이트
        for (const category of categories) {
          await this.product_category_repository
            .with_transaction(manager)
            .update({ ...category, product_id });
        }

        // 상품 제품 업데이트
        return this.repository.with_transaction(manager).update(
          {
            seller_id,
            brand_id,
            ...product,
          },
          product_id,
        );
      });

      if (!updated_product_entity) {
        throw new Error("상품 업데이트에 실패했습니다.");
      }

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
