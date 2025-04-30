import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Category } from "src/domain/entities";
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
    const seller = await this.entity_manager.findOne(SellerEntity, { where: { id: seller_id } });

    if (!seller) {
      throw new Error(`Seller with id ${seller_id} not found`);
    }
    const brand = await this.entity_manager.findOne(BrandEntity, { where: { id: brand_id } });
    if (!brand) {
      throw new Error(`Brand with id ${brand_id} not found`);
    }

    const product_entity = this.entity_manager.create(ProductEntity, { ...product, seller, brand });
    await this.entity_manager.save(product_entity);

    const detail_entity = this.entity_manager.create(ProductDetailEntity, {
      ...detail,
      product: product_entity,
    });
    await this.entity_manager.save(detail_entity);

    const price_entity = this.entity_manager.create(ProductPriceEntity, {
      ...price,
      product: product_entity,
    });
    await this.entity_manager.save(price_entity);

    const category_entities = this.entity_manager.create(
      ProductCategoryEntity,
      await Promise.all(
        categories.map(async ({ category_id, is_primary }: Product_Category) => {
          const found_category = await this.entity_manager.findOne(ProductCategoryEntity, {
            where: { id: category_id },
          });
          if (!found_category) {
            throw new Error(`Category with id ${category_id} not found`);
          }

          return { category: found_category, is_primary, product: product_entity };
        }),
      ),
    );
    await this.entity_manager.save(category_entities);

    for (const option_group of option_groups) {
      const { options, ...group_entity } = option_group;

      const option_group_entity = this.entity_manager.create(ProductOptionGroupEntity, {
        ...group_entity,
        product: product_entity,
      });
      await this.entity_manager.save(option_group_entity);

      const option_entities = this.entity_manager.create(
        ProductOptionEntity,
        options.map((option) => ({ ...option, option_group: option_group_entity })),
      );
      await this.entity_manager.save(option_entities);
    }

    const image_entities = this.entity_manager.create(
      ProductImageEntity,
      images.map((image) => ({ ...image, product: product_entity })),
    );
    await this.entity_manager.save(image_entities);

    const tag_entities = this.entity_manager.create(
      ProductTagEntity,
      await Promise.all(
        tag_ids.map(async (tag_id) => {
          const tag = await this.entity_manager.findOne(ProductTagEntity, {
            where: { id: tag_id },
          });
          if (!tag) {
            throw new Error(`Tag with id ${tag_id} not found`);
          }

          return { ...tag, product: product_entity };
        }),
      ),
    );
    await this.entity_manager.save(tag_entities);

    return product_entity;
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
