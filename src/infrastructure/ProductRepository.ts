import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";

import {
  Product,
  Product_Category,
  Product_Detail,
  Product_Image,
  Product_Option,
  Product_Option_Group,
  Product_Price,
  Product_Tag,
} from "src/domain";
import IRepository from "./IRepository";
import {
  BrandEntity,
  CategoryEntity,
  ProductCategoryEntity,
  ProductDetailEntity,
  ProductImageEntity,
  ProductOptionEntity,
  ProductOptionGroupEntity,
  ProductPriceEntity,
  ProductTagEntity,
  SellerEntity,
} from "./entities";
import ProductEntity from "./entities/Product.entity";

@Injectable()
export default class ProductRepository implements IRepository<ProductEntity> {
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
  ) {}

  async create({
    product,
    detail,
    price,
    categories,
    option_groups,
    images,
    tags,
  }: {
    product: Product;
    detail: Product_Detail;
    price: Product_Price;
    categories: Product_Category[];
    option_groups: Product_Option_Group[];
    images: Product_Image[];
    tags: Product_Tag[];
  }): Promise<ProductEntity> {
    const seller = await this.sellerRepository.findOne({ where: { id: product.seller.id } });
    if (!seller) {
      throw new Error(`Seller with id ${product.seller.id} not found`);
    }
    const brand = await this.brandRepository.findOne({ where: { id: product.brand.id } });
    if (!brand) {
      throw new Error(`Brand with id ${product.brand.id} not found`);
    }
    const productEntity = this.productRepository.create({ ...product, seller, brand });
    await this.productRepository.save(productEntity);

    const detailEntity = this.productDetailRepository.create({ ...detail, product: productEntity });
    await this.productDetailRepository.save(detailEntity);

    const priceEntity = this.productPriceRepository.create({ ...price, product: productEntity });
    await this.productPriceRepository.save(priceEntity);

    const categoryEntities = this.productCategoryRepository.create(
      await Promise.all(
        categories.map(async ({ category, is_primary }: Product_Category) => {
          const foundCategory = await this.categoryRepository.findOne({
            where: { id: category.id },
          });
          if (!foundCategory) {
            throw new Error(`Category with id ${category.id} not found`);
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
        options.map((option: Product_Option) => ({ ...option, optionGroup: optionGroupEntity })),
      );

      await this.productOptionRepository.save(optionEntities);
      await this.productOptionGroupRepository.save(optionGroupEntity);
    }

    const imageEntities = this.productImageRepository.create(
      images.map((image) => ({ ...image, product: productEntity })),
    );
    await this.productImageRepository.save(imageEntities);

    const tagEntities = this.productTagRepository.create(
      tags.map((tag) => ({ ...tag, product: productEntity })),
    );
    await this.productTagRepository.save(tagEntities);

    return productEntity;
  }

  async findAll({
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
  }): Promise<ProductEntity[]> {
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

    return query.getMany();
  }

  async findById(id: number): Promise<ProductEntity | null> {
    return await this.productRepository.findOne({ where: { id } });
  }

  async update(id: number, data: Partial<ProductEntity>): Promise<ProductEntity> {
    await this.productRepository.update(id, data);

    const updatedProduct = await this.findById(id);
    if (!updatedProduct) {
      throw new Error(`Product with id ${id} not found`);
    }

    return updatedProduct;
  }

  async delete(id: number): Promise<void> {
    await this.productRepository.delete(id);
  }
}
