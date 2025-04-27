import { Repository } from "typeorm";
import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";

import { Product_Category } from "src/domain";
import {
  BrandEntity,
  CategoryEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductEntity,
  ProductImageEntity,
  ProductOptionEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
  TagEntity,
} from "src/infrastructure/entities";
import { ProductInputDTO } from "./dto/ProductInputDTO";

@Injectable()
export default class ProductService {
  constructor(
    @InjectRepository(ProductEntity)
    private readonly productRepository: Repository<ProductEntity>,
    @InjectRepository(ProductDetailEntity)
    private readonly productDetailRepository: Repository<ProductDetailEntity>,
    @InjectRepository(ProductPriceEntity)
    private readonly productPriceRepository: Repository<ProductPriceEntity>,
    @InjectRepository(ProductCategoryEntity)
    private readonly productCategoryRepository: Repository<ProductCategoryEntity>,
    @InjectRepository(ProductOptionGroupEntity)
    private readonly productOptionGroupRepository: Repository<ProductOptionGroupEntity>,
    @InjectRepository(ProductOptionEntity)
    private readonly productOptionRepository: Repository<ProductOptionEntity>,
    @InjectRepository(ProductImageEntity)
    private readonly productImageRepository: Repository<ProductImageEntity>,
    @InjectRepository(ProductTagEntity)
    private readonly productTagRepository: Repository<ProductTagEntity>,
    @InjectRepository(SellerEntity)
    private readonly sellerRepository: Repository<SellerEntity>,
    @InjectRepository(BrandEntity)
    private readonly brandRepository: Repository<BrandEntity>,
    @InjectRepository(CategoryEntity)
    private readonly categoryRepository: Repository<CategoryEntity>,
    @InjectRepository(ProductTagEntity)
    private readonly tagRepository: Repository<TagEntity>,
  ) {}

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

    const seller = await this.sellerRepository.findOne({ where: { id: seller_id } });
    if (!seller) {
      throw new Error(`Seller with id ${seller_id} not found`);
    }
    const brand = await this.brandRepository.findOne({ where: { id: brand_id } });
    if (!brand) {
      throw new Error(`Brand with id ${brand_id} not found`);
    }

    const productEntity = this.productRepository.create({ ...product, seller, brand });
    await this.productRepository.save(productEntity);

    const detailEntity = this.productDetailRepository.create({ ...detail, product: productEntity });
    await this.productDetailRepository.save(detailEntity);

    const priceEntity = this.productPriceRepository.create({ ...price, product: productEntity });
    await this.productPriceRepository.save(priceEntity);

    const categoryEntities = this.productCategoryRepository.create(
      await Promise.all(
        categories.map(async ({ category_id, is_primary }: Product_Category) => {
          const foundCategory = await this.categoryRepository.findOne({
            where: { id: category_id },
          });
          if (!foundCategory) {
            throw new Error(`Category with id ${category_id} not found`);
          }

          return { category: foundCategory, is_primary, product: productEntity };
        }),
      ),
    );
    await this.productCategoryRepository.save(categoryEntities);

    for (const optionGroup of option_groups) {
      const { options, ...groupEntity } = optionGroup;

      const optionGroupEntity = this.productOptionGroupRepository.create({
        ...groupEntity,
        product: productEntity,
      });

      const optionEntities = this.productOptionRepository.create(
        options.map((option) => ({ ...option, optionGroup: optionGroupEntity })),
      );

      await this.productOptionRepository.save(optionEntities);
      await this.productOptionGroupRepository.save(optionGroupEntity);
    }

    const imageEntities = this.productImageRepository.create(
      images.map((image) => ({ ...image, product: productEntity })),
    );
    await this.productImageRepository.save(imageEntities);

    const tagEntities = this.productTagRepository.create(
      await Promise.all(
        tag_ids.map(async (tag_id) => {
          const tag = await this.tagRepository.findOne({
            where: { id: tag_id },
          });
          if (!tag) {
            throw new Error(`Tag with id ${tag_id} not found`);
          }

          return { ...tag, product: productEntity };
        }),
      ),
    );
    await this.productTagRepository.save(tagEntities);

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

    const query = this.productRepository
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
    return await this.productRepository.findOne({ where: { id } });
  }

  async update(id: number, data: ProductInputDTO) {
    await this.productRepository.update(id, data);

    const updatedProduct = await this.getById(id);
    if (!updatedProduct) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updatedProduct;
  }

  async delete(id: number) {
    await this.productRepository.delete(id);
  }
}
