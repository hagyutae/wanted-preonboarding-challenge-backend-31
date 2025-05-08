import { Inject, Injectable, NotFoundException } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { IBaseRepository } from "@libs/domain/repositories";
import { Product, Product_Detail, Product_Image, Product_Price } from "@product/domain/entities";
import {
  FilterDTO,
  ProductCatalogDTO,
  ProductCategoryDTO,
  ProductInputDTO,
  ProductOptionGroupDTO,
  ProductSummaryDTO,
  ProductTagDTO,
} from "../dto";

@Injectable()
export default class ProductService {
  constructor(
    private readonly entity_manager: EntityManager,
    @Inject("IProductRepository")
    private readonly repository: IBaseRepository<Product | ProductSummaryDTO | ProductCatalogDTO>,
    @Inject("IProductDetailRepository")
    private readonly product_detail_repository: IBaseRepository<Product_Detail>,
    @Inject("IProductPriceRepository")
    private readonly product_price_repository: IBaseRepository<Product_Price>,
    @Inject("IProductCategoryRepository")
    private readonly product_category_repository: IBaseRepository<ProductCategoryDTO>,
    @Inject("IProductOptionGroupRepository")
    private readonly product_option_group_repository: IBaseRepository<ProductOptionGroupDTO>,
    @Inject("IProductImageRepository")
    private readonly product_image_repository: IBaseRepository<Product_Image>,
    @Inject("IProductTagRepository")
    private readonly product_tag_repository: IBaseRepository<ProductTagDTO>,
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
    // 상품 등록 트랜잭션 처리
    const product_entity = await this.entity_manager.transaction(async (manager) => {
      // 상품 등록
      const product_entity = await this.repository.with_transaction(manager).save({
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
      id: id!,
      name,
      slug,
      created_at: created_at!,
      updated_at: updated_at!,
    }))(product_entity);
  }

  async find_all({ page = 1, per_page = 10, sort, ...rest }: FilterDTO) {
    const [sort_field, sort_order] = sort?.split(":") ?? ["created_at", "DESC"];

    const items = (await this.repository.find_by_filters({
      page,
      per_page,
      sort_field,
      sort_order,
      ...rest,
    })) as ProductSummaryDTO[];

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
    const product = await this.repository.find_by_id(id);

    if (!product) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Product", resourceId: id },
      });
    }
    return product as ProductCatalogDTO;
  }

  async edit(
    product_id: number,
    { detail, seller_id, brand_id, price, categories, ...product }: ProductInputDTO,
  ) {
    const is_updated = await this.entity_manager.transaction(async (manager) => {
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

    if (!is_updated) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Product", resourceId: product_id },
      });
    }

    const updated_product = await this.repository.find_by_id(product_id);

    return (({ id, name, slug, updated_at }) => ({
      id: id!,
      name,
      slug,
      updated_at: updated_at!,
    }))(updated_product!);
  }

  async remove(id: number) {
    const is_deleted = await this.repository.delete(id);

    if (!is_deleted) {
      throw new NotFoundException({
        message: "요청한 리소스를 찾을 수 없습니다.",
        details: { resourceType: "Product", resourceId: id },
      });
    }
  }
}
