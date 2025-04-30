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
  ProductDetailEntity,
  ProductEntity,
  ProductPriceEntity,
  SellerEntity,
} from "../entities";
import { ProductDetailView, ProductSummaryView } from "../views";
import ProductCategoryRepository from "./Product_Category.repository";
import ProductDetailRepository from "./Product_Detail.repository";
import ProductImageRepository from "./Product_Image.repository";
import ProductOptionGroupRepository from "./Product_Option_Group.repository";
import ProductPriceRepository from "./Product_Price.repository";
import ProductTagRepository from "./Product_Tag.repository";

@Injectable()
export default class ProductRepository {
  constructor(
    private readonly entity_manager: EntityManager,
    private readonly product_detail_repository: ProductDetailRepository,
    private readonly product_category_repository: ProductCategoryRepository,
    private readonly product_price_repository: ProductPriceRepository,
    private readonly product_option_group_repository: ProductOptionGroupRepository,
    private readonly product_image_repository: ProductImageRepository,
    private readonly product_tag_repository: ProductTagRepository,
  ) {}

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
          seller: { id: seller_id },
          brand: { id: brand_id },
        });

        // 상품 상세 등록
        await this.product_detail_repository.save(detail, product_entity.id);

        // 상품 가격 등록
        await this.product_price_repository.save(price, product_entity.id);

        // 상품 카테고리 등록
        await this.product_category_repository.save(categories, product_entity.id);

        // 상품 옵션 등록
        await this.product_option_group_repository.save(option_groups, product_entity.id);

        // 상품 이미지 등록
        await this.product_image_repository.saves(images, product_entity.id);

        // 상품 태그 등록
        await this.product_tag_repository.save(tag_ids, product_entity.id);

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
        await this.product_detail_repository.update(detail, id);

        // 상품 가격 업데이트
        await this.product_price_repository.update(price, id);

        // 상품 카테고리 업데이트
        await this.product_category_repository.update(categories, id);
        // 상품 제품 업데이트
        const updated_product_entity = await manager.save(ProductEntity, {
          id,
          seller: { id: seller_id },
          brand: { id: brand_id },
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
