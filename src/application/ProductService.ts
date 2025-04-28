import { EntityManager } from "typeorm";
import { Injectable } from "@nestjs/common";

import { Product_Category } from "src/domain";
import {
  BrandEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductEntity,
  ProductImageEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
  ReviewEntity,
} from "src/infrastructure/entities";
import { ProductInputDTO } from "./dto/ProductInputDTO";

@Injectable()
export default class ProductService {
  constructor(private readonly entityManager: EntityManager) {}

  async create(params: any) {
    const {
      detail,
      price,
      categories,
      option_groups,
      images,
      tags: tag_ids,
      seller_id,
      brand_id,
      ...product
    }: ProductInputDTO = params;

    const seller = await this.entityManager.findOne(SellerEntity, { where: { id: seller_id } });

    if (!seller) {
      throw new Error(`Seller with id ${seller_id} not found`);
    }
    const brand = await this.entityManager.findOne(BrandEntity, { where: { id: brand_id } });
    if (!brand) {
      throw new Error(`Brand with id ${brand_id} not found`);
    }

    const productEntity = this.entityManager.create(ProductEntity, { ...product, seller, brand });
    await this.entityManager.save(productEntity);

    const detailEntity = this.entityManager.create(ProductDetailEntity, {
      ...detail,
      product: productEntity,
    });
    await this.entityManager.save(detailEntity);

    const priceEntity = this.entityManager.create(ProductPriceEntity, {
      ...price,
      product: productEntity,
    });
    await this.entityManager.save(priceEntity);

    const categoryEntities = this.entityManager.create(
      ProductCategoryEntity,
      await Promise.all(
        categories.map(async ({ category_id, is_primary }: Product_Category) => {
          const foundCategory = await this.entityManager.findOne(ProductCategoryEntity, {
            where: { id: category_id },
          });
          if (!foundCategory) {
            throw new Error(`Category with id ${category_id} not found`);
          }

          return { category: foundCategory, is_primary, product: productEntity };
        }),
      ),
    );
    await this.entityManager.save(categoryEntities);

    for (const optionGroup of option_groups) {
      const { options, ...groupEntity } = optionGroup;

      const optionGroupEntity = this.entityManager.create(ProductOptionGroupEntity, {
        ...groupEntity,
        product: productEntity,
      });

      const optionEntities = this.entityManager.create(
        ProductOptionGroupEntity,
        options.map((option) => ({ ...option, optionGroup: optionGroupEntity })),
      );

      await this.entityManager.save(optionEntities);
      await this.entityManager.save(optionGroupEntity);
    }

    const imageEntities = this.entityManager.create(
      ProductImageEntity,
      images.map((image) => ({ ...image, product: productEntity })),
    );
    await this.entityManager.save(imageEntities);

    const tagEntities = this.entityManager.create(
      ProductTagEntity,
      await Promise.all(
        tag_ids.map(async (tag_id) => {
          const tag = await this.entityManager.findOne(ProductTagEntity, {
            where: { id: tag_id },
          });
          if (!tag) {
            throw new Error(`Tag with id ${tag_id} not found`);
          }

          return { ...tag, product: productEntity };
        }),
      ),
    );
    await this.entityManager.save(tagEntities);

    return productEntity;
  }

  getProductWithAggregatesQuery() {
    return this.entityManager
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .innerJoin(ProductPriceEntity, "product_prices", "product_prices.product_id = products.id")
      .leftJoin(
        ProductCategoryEntity,
        "product_categories",
        "product_categories.product_id = products.id",
      )
      .leftJoin(ProductImageEntity, "product_images", "product_images.product_id = products.id")
      .leftJoin(ReviewEntity, "reviews", "reviews.product_id = products.id")
      .leftJoin(BrandEntity, "brands", "brands.id = products.brand_id")
      .leftJoin(SellerEntity, "sellers", "sellers.id = products.seller_id")
      .select([
        "products.id as id",
        "products.name as name",
        "products.slug as slug",
        "products.short_description as short_description",
        "product_prices.base_price as base_price",
        "product_prices.sale_price as sale_price",
        "product_prices.currency as currency",
        "product_images.url as image_url",
        "product_images.alt_text as image_alt_text",
        "brands.id as brand_id",
        "brands.name as brand_name",
        "sellers.id as sellers_id",
        "sellers.name as seller_name",
        "products.status as status",
        "products.created_at as created_at",
      ])
      .addSelect("ROUND(AVG(reviews.rating), 1)", "rating")
      .addSelect("COUNT(reviews.id)", "review_count")
      .groupBy("products.id")
      .addGroupBy("products.created_at")
      .addGroupBy("product_prices.base_price")
      .addGroupBy("product_prices.sale_price")
      .addGroupBy("product_prices.currency")
      .addGroupBy("product_images.url")
      .addGroupBy("product_images.alt_text")
      .addGroupBy("brands.id")
      .addGroupBy("brands.name")
      .addGroupBy("sellers.id")
      .addGroupBy("sellers.name");
  }

  async getAll({
    page = 1,
    perPage = 10,
    sort,
    status,
    minPrice,
    maxPrice,
    category,
    seller,
    brand,
    search,
  }: {
    page?: number;
    perPage?: number;
    sort?: string;
    status?: string;
    minPrice?: number;
    maxPrice?: number;
    category?: number[];
    seller?: number;
    brand?: number;
    inStock?: boolean;
    search?: string;
  }) {
    // 상품 집계 처리 쿼리
    const innerQuery = this.getProductWithAggregatesQuery();

    // 쿼리 필터링
    innerQuery
      .andWhere(status ? "products.status = :status" : "1=1", { status })
      .andWhere(minPrice ? "product_prices.base_price >= :minPrice" : "1=1", { minPrice })
      .andWhere(maxPrice ? "product_prices.base_price <= :maxPrice" : "1=1", { maxPrice })
      .andWhere(category ? "product_categories.id IN (:...category)" : "1=1", { category })
      .andWhere(seller ? "products.seller_id = :seller" : "1=1", { seller })
      .andWhere(brand ? "products.brand_id = :brand" : "1=1", { brand })
      .andWhere(search ? "products.name LIKE :search" : "1=1", { search: `%${search}%` });

    // 쿼리 정렬, 페이지네이션
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];
    const query = this.entityManager
      .createQueryBuilder()
      .select("*")
      .from(`(${innerQuery.getQuery()})`, "result")
      .orderBy(`result.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * perPage)
      .limit(perPage)
      .setParameters(innerQuery.getParameters());

    // 쿼리 실행
    const items = await query.getRawMany();

    return {
      items,
      pagination: {
        total_items: items.length,
        total_pages: Math.ceil(items.length / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  }

  async getById(id: number) {
    return await this.entityManager.findOne(ProductEntity, { where: { id } });
  }

  async update(id: number, data: ProductInputDTO) {
    await this.entityManager.update(ProductEntity, id, data);

    const updatedProduct = await this.getById(id);
    if (!updatedProduct) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updatedProduct;
  }

  async delete(id: number) {
    await this.entityManager.delete(ProductEntity, id);
  }
}
