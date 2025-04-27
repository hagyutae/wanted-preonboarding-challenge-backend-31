import { Injectable } from "@nestjs/common";
import { EntityManager } from "typeorm";

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

  async getAll({
    status,
    search,
    sort,
    page = 1,
    perPage = 10,
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
    const [field, order] = sort?.split(":") ?? ["created_at", "DESC"];

    const query = this.entityManager
      .getRepository(ProductEntity)
      .createQueryBuilder("products")
      .where("1 = 1") // 조건 기본값
      .andWhere(status ? "products.status = :status" : "1=1", { status })
      // .andWhere(minPrice ? "products.price >= :minPrice" : "1=1", { minPrice })
      // .andWhere(maxPrice ? "products.price <= :maxPrice" : "1=1", { maxPrice })
      // .andWhere(category ? "products.categoryId = :category" : "1=1", { category })
      // .andWhere(seller ? "products.sellerId = :seller" : "1=1", { seller })
      // .andWhere(brand ? "products.brandId = :brand" : "1=1", { brand })
      // .andWhere(typeof inStock === "boolean" ? "products.stock > 0" : "1=1")
      .andWhere(search ? "products.name LIKE :search" : "1=1", { search: `%${search}%` })
      .orderBy(`products.${field}`, order.toUpperCase() as "ASC" | "DESC")
      .skip((page - 1) * perPage)
      .take(perPage);

    const items = await query.getMany();

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
