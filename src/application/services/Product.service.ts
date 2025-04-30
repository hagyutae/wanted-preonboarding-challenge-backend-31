import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import {
  BrandEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductEntity,
  ProductImageEntity,
  ProductOptionEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
} from "src/infrastructure/entities";
import { ProductDetailView, ProductSummaryView } from "src/infrastructure/views";
import { ProductInputDTO, FilterDTO } from "../dto";

@Injectable()
export default class ProductService {
  constructor(private readonly entity_manager: EntityManager) {}

  async create({
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
    // 상품 등록
    const product_entity = await this.entity_manager.save(ProductEntity, {
      ...product,
      seller: { id: seller_id } as SellerEntity,
      brand: { id: brand_id } as BrandEntity,
    });

    // 상품 상세 등록
    await this.entity_manager.save(ProductDetailEntity, {
      ...detail,
      product: product_entity,
    });

    // 상품 가격 등록
    await this.entity_manager.save(ProductPriceEntity, {
      ...price,
      product: product_entity,
    });

    // 상품 카테고리 등록
    await this.entity_manager.save(
      ProductCategoryEntity,
      categories.map(({ category_id, is_primary }) => ({
        category: { id: category_id } as ProductCategoryEntity,
        is_primary,
        product: product_entity,
      })),
    );

    // 상품 옵션 등록
    for (const { options, ...group_entity } of option_groups) {
      // 상품 옵션 그룹 등록
      const option_group_entity = await this.entity_manager.save(ProductOptionGroupEntity, {
        ...group_entity,
        product: product_entity,
      });

      // 상품 옵션 그룹에 속한 옵션 등록
      await this.entity_manager.save(
        ProductOptionEntity,
        options.map((option) => ({ ...option, option_group: option_group_entity })),
      );
    }

    // 상품 이미지 등록
    await this.entity_manager.save(
      ProductImageEntity,
      images.map((image) => ({ ...image, product: product_entity })),
    );

    // 상품 태그 등록
    await this.entity_manager.save(
      ProductTagEntity,
      tag_ids.map((tag_id) => ({
        tag: { id: tag_id } as ProductTagEntity,
        product: product_entity,
      })),
    );

    // 상품 등록 결과 반환
    return (({ id, name, slug, created_at, updated_at }) => ({
      id,
      name,
      slug,
      created_at,
      updated_at,
    }))(product_entity);
  }

  async get_all({
    page = 1,
    per_page = 10,
    sort,
    status,
    min_price,
    max_price,
    category,
    seller,
    brand,
    search,
  }: FilterDTO) {
    // 상품 집계 처리 쿼리
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.entity_manager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .andWhere(status ? "summary.status = :status" : "1=1", { status })
      .andWhere(min_price ? "summary.base_price >= :minPrice" : "1=1", { minPrice: min_price })
      .andWhere(max_price ? "summary.base_price <= :maxPrice" : "1=1", { maxPrice: max_price })
      .andWhere(category ? "summary.id IN (:...category)" : "1=1", { category })
      .andWhere(seller ? "summary.seller->>'id' = :seller" : "1=1", { seller })
      .andWhere(brand ? "summary.brand->>'id' = :brand" : "1=1", { brand })
      .andWhere(search ? "summary.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`summary.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * per_page)
      .limit(per_page);

    // 쿼리 실행
    const items = await query.getMany();

    return {
      items,
      pagination: {
        total_items: items.length,
        total_pages: Math.ceil(items.length / per_page),
        current_page: page,
        per_page: per_page,
      },
    };
  }

  async get_by_id(id: number) {
    return this.entity_manager.findOne(ProductDetailView, { where: { id } });
  }

  async update(id: number, data: ProductInputDTO) {
    await this.entity_manager.update(ProductEntity, id, data);

    const updated_product = await this.get_by_id(id);
    if (!updated_product) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updated_product;
  }

  async delete(id: number) {
    await this.entity_manager.delete(ProductEntity, id);
  }
}
