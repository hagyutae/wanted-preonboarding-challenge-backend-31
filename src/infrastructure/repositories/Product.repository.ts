import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import {
  Product,
  Product_Category,
  Product_Detail,
  Product_Image,
  Product_Option_Group,
  Product_Price,
} from "src/domain/entities";
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
} from "../entities";
import { ProductDetailView, ProductSummaryView } from "../views";

@Injectable()
export default class ProductRepository {
  constructor(private readonly entity_manager: EntityManager) {}

  async save({
    product,
    detail,
    price,
    categories,
    option_groups,
    images,
    tag_ids,
    seller_id,
    brand_id,
  }: {
    product: Product;
    detail: Product_Detail;
    price: Product_Price;
    categories: Product_Category[];
    option_groups: Product_Option_Group[];
    images: Product_Image[];
    tag_ids: number[];
    seller_id: number;
    brand_id: number;
  }) {
    try {
      const product_entity = await this.entity_manager.transaction(async (manager) => {
        // 상품 등록
        const product_entity = await manager.save(ProductEntity, {
          ...product,
          seller: { id: seller_id } as SellerEntity,
          brand: { id: brand_id } as BrandEntity,
        });

        // 상품 상세 등록
        await manager.save(ProductDetailEntity, {
          ...detail,
          product: product_entity,
        });

        // 상품 가격 등록
        await manager.save(ProductPriceEntity, {
          ...price,
          product: product_entity,
        });

        // 상품 카테고리 등록
        await manager.save(
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
          const option_group_entity = await manager.save(ProductOptionGroupEntity, {
            ...group_entity,
            product: product_entity,
          });

          // 상품 옵션 그룹에 속한 옵션 등록
          await manager.save(
            ProductOptionEntity,
            options.map((option) => ({ ...option, option_group: option_group_entity })),
          );
        }

        // 상품 이미지 등록
        await manager.save(
          ProductImageEntity,
          images.map((image) => ({ ...image, product: product_entity })),
        );

        // 상품 태그 등록
        await manager.save(
          ProductTagEntity,
          tag_ids.map((tag_id) => ({
            tag: { id: tag_id } as ProductTagEntity,
            product: product_entity,
          })),
        );

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

  async find_by_filters({
    page,
    per_page,
    sort_field,
    sort_order,
    status,
    min_price,
    max_price,
    category,
    seller,
    brand,
    search,
  }: {
    page: number;
    per_page: number;
    sort_field: string;
    sort_order: string;
    status?: string;
    min_price?: number;
    max_price?: number;
    category?: number[];
    seller?: number;
    brand?: number;
    search?: string;
  }) {
    // 상품 집계 처리 쿼리
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
      .orderBy(`summary.${sort_field}`, sort_order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * per_page)
      .limit(per_page);

    // 쿼리 실행
    return await query.getMany();
  }

  async get_by_id(id: number) {
    return this.entity_manager.findOne(ProductDetailView, { where: { id } });
  }

  async update(
    id: number,
    {
      product,
      seller_id,
      brand_id,
      detail,
      price,
      categories,
    }: {
      product: Product;
      seller_id: number;
      brand_id: number;
      detail: Product_Detail;
      price: Product_Price;
      categories: Product_Category[];
    },
  ) {
    try {
      return await this.entity_manager.transaction(async (manager) => {
        // 상품 디테일 업데이트
        await manager
          .createQueryBuilder()
          .update(ProductDetailEntity)
          .set({ ...detail })
          .where("product_id = :product_id", { product_id: id })
          .execute();

        // 상품 가격 업데이트
        await manager
          .createQueryBuilder()
          .update(ProductPriceEntity)
          .set({ ...price })
          .where("product_id = :product_id", { product_id: id })
          .execute();

        // 상품 카테고리 업데이트
        for (const { category_id, is_primary } of categories) {
          await manager
            .createQueryBuilder()
            .update(ProductCategoryEntity)
            .set({ is_primary, category: { id: category_id } })
            .where("product_id = :product_id", { product_id: id })
            .execute();
        }

        // 상품 제품 업데이트
        const updated_product_entity = await manager.save(ProductEntity, {
          id,
          seller: { id: seller_id } as SellerEntity,
          brand: { id: brand_id } as BrandEntity,
          ...product,
        });

        return updated_product_entity;
      });
    } catch (error) {
      throw new Error((error as Error).message);
    }
  }

  async delete(id: number) {
    await this.entity_manager.delete(ProductEntity, id);
  }
}
