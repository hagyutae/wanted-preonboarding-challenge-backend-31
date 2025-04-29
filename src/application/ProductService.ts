import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

import { Product_Category } from "src/domain";
import {
  BrandEntity,
  ProductEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductImageEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
} from "src/infrastructure/entities";
import { ProductSummaryView, ProductDetailView } from "src/infrastructure/views";
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
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.entityManager
      .getRepository(ProductSummaryView)
      .createQueryBuilder("summary")
      .andWhere(status ? "summary.status = :status" : "1=1", { status })
      .andWhere(minPrice ? "summary.base_price >= :minPrice" : "1=1", { minPrice })
      .andWhere(maxPrice ? "summary.base_price <= :maxPrice" : "1=1", { maxPrice })
      .andWhere(category ? "summary.id IN (:...category)" : "1=1", { category })
      .andWhere(seller ? "summary.seller_id = :seller" : "1=1", { seller })
      .andWhere(brand ? "summary.brand_id = :brand" : "1=1", { brand })
      .andWhere(search ? "summary.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`summary.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .offset((page - 1) * perPage)
      .limit(perPage);

    // 쿼리 실행
    const items = await query.getMany();

    return {
      items: items.map(
        ({
          primary_image,
          product_images_url,
          product_images_alt_text,
          brand,
          brands_id,
          brands_name,
          seller,
          sellers_id,
          sellers_name,
          ...rest
        }) => ({
          ...rest,
          primary_image,
          brand,
          seller,
        }),
      ),
      pagination: {
        total_items: items.length,
        total_pages: Math.ceil(items.length / perPage),
        current_page: page,
        per_page: perPage,
      },
    };
  }

  async getById(id: number) {
    const item = await this.entityManager.findOne(ProductDetailView, {
      where: { id },
    });

    if (!item) {
      throw new Error(`Product with id ${id} not found`);
    }

    const {
      seller,
      brand,
      detail,
      price,
      categories,
      option_groups,
      images,
      tags,
      rating,
      ...remain
    } = item;

    return {
      ...remain,
      seller,
      brand,
      detail,
      price,
      categories,
      option_groups,
      images,
      tags,
      rating,
    };
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
