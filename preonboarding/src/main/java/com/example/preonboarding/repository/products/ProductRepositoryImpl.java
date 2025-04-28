package com.example.preonboarding.repository.products;

import com.example.preonboarding.domain.*;
import com.example.preonboarding.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<ProductsDTO> searchPage(ProductSearchRequest request) {
        QProducts products = QProducts.products;
        QProductPrices prices = QProductPrices.productPrices;
        QProductTags tags = QProductTags.productTags;
        QProductCategories categories = QProductCategories.productCategories;
        QBrands brands = QBrands.brands;
        QSellers sellers = QSellers.sellers;
        QProductImages images = QProductImages.productImages;
        QReviews reviews = QReviews.reviews;
        QProductOption options = QProductOption.productOption;
        QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;


        List<BooleanExpression> predicates = buildSearchPredicates(request, products, prices, brands, sellers, categories, tags, options);


        List<ProductsDTO> productList = queryFactory
                .select(new QProductsDTO(
                        products.id,
                        products.name,
                        products.slug,
                        products.shortDescription,
                        prices.basePrice,
                        prices.salePrice,
                        prices.currency,
                        brands.id,
                        brands.name,
                        sellers.id,
                        sellers.name,
                        products.status,
                        products.createdAt
                ))
                .from(products)
                .join(products.productPrices, prices)
                .leftJoin(products.brands, brands)
                .leftJoin(products.sellers, sellers)
                .leftJoin(products.productCategories, categories)
                .leftJoin(products.productTags, tags)
                .where(predicates.toArray(new BooleanExpression[0]))
                .groupBy(
                        products.id,
                        products.name,
                        products.slug,
                        products.shortDescription,
                        products.status,
                        products.createdAt,
                        prices.basePrice,
                        prices.salePrice,
                        prices.currency,
                        brands.id,
                        brands.name,
                        sellers.id,
                        sellers.name
                )
                .offset(request.getPage()-1)
                .limit(request.getPerPage())
                .fetch();

        if(productList.isEmpty()) return productList;

        List<Long> productIds = productList.stream()
                .map(ProductsDTO::getId)
                .collect(Collectors.toList());

        attachImages(productList, productIds,images);
        attachOptions(productList,productIds,options,optionGroup);
        attachReviews(productList, productIds,reviews);


        return productList;
    }

    @Override
    public ProductsDTO findProductsById(Long id) {

        QProducts products = QProducts.products;
        QProductPrices prices = QProductPrices.productPrices;
        QProductTags tags = QProductTags.productTags;
        QProductCategories categories = QProductCategories.productCategories;
        QBrands brands = QBrands.brands;
        QSellers sellers = QSellers.sellers;
        QProductImages images = QProductImages.productImages;
        QReviews reviews = QReviews.reviews;
        QProductOption options = QProductOption.productOption;
        QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;


        return queryFactory.select(new QProductsDTO(
                    products.id,
                    products.name,
                    products.slug,
                    products.shortDescription,
                    products.fullDescription,
                    sellers.id,
                    sellers.name,
                    sellers.description,
                    sellers.logoUrl,
                    sellers.rating,
                    sellers.contactEmail,
                    sellers.contactPhone,
                    brands.id,
                    brands.name,
                    products.status,
                    products.createdAt
                ))
                .from(products)
                .distinct()
                .join(products.sellers,sellers)
                .join(products.brands,brands)
               .where(products.id.eq(id))
               .fetchOne();
    }

    private void attachImages(List<ProductsDTO> products, List<Long> productsIds, QProductImages images){
        List<ProductImageDTO> imageList = queryFactory
                .select(new QProductImageDTO(
                        images.products.id,
                        images.url,
                        images.altText
                ))
                .from(images)
                .where(images.products.id.in(productsIds)
                        .and(images.isPrimary.eq(true)))
                .fetch();


        Map<Long, ProductImageDTO> imageMap = imageList.stream()
                .collect(Collectors.toMap(ProductImageDTO::getProductsId, Function.identity()));

        for (ProductsDTO product : products) {
            product.setImages(imageMap.getOrDefault(product.getId(), null));
        }

    }

    private void attachOptions(List<ProductsDTO> products, List<Long> productsIds, QProductOption option,QProductOptionGroup optionGroup){
        List<ProductOptionDTO> optionList = queryFactory.select(new QProductOptionDTO(
                            optionGroup.products.id,
                            option.stock.sum()
                        )
                ).from(option)
                .rightJoin(option.optionGroups,optionGroup)
                .where(option.optionGroups.products.id.in(productsIds))
                .groupBy(optionGroup.products.id)
                .fetch();


        Map<Long, ProductOptionDTO> optionMap = optionList.stream()
                .collect(Collectors.toMap(ProductOptionDTO::getProductsId, Function.identity()));

        for (ProductsDTO product : products) {
            product.setOptions(optionMap.getOrDefault(product.getId(),null));
        }
    }

    private void attachReviews(List<ProductsDTO> products, List<Long> productsIds, QReviews reviews){
        List<ReviewDTO> reviewList = queryFactory.select(new QReviewDTO(
                        reviews.products.id,
                        reviews.rating.avg(),
                        reviews.count()
                )).from(reviews)
                .where(reviews.products.id.in(productsIds))
                .groupBy(reviews.products.id).fetch();

        Map<Long, ReviewDTO> reviewMap = reviewList.stream().collect(Collectors.toMap(ReviewDTO::getProductsId, Function.identity()));

        for (ProductsDTO product : products) {
            product.setReviews(reviewMap.getOrDefault(product.getId(), null));
        }

    }

    private List<BooleanExpression> buildSearchPredicates (ProductSearchRequest request, QProducts products, QProductPrices prices, QBrands brands, QSellers sellers, QProductCategories categories, QProductTags tags, QProductOption options) {


        List<BooleanExpression> predicates = new ArrayList<>();

        if (request.getStatus() != null) {
            predicates.add(products.status.eq(request.getStatus()));
        }

        if (request.getMinPrice() != null){
            predicates.add(prices.basePrice.goe(request.getMinPrice()));
        }

        if (request.getMaxPrice() != null){
            predicates.add(prices.basePrice.loe(request.getMaxPrice()));
        }

        if (request.getBrand() != null) {
            predicates.add(brands.id.eq(request.getBrand()));
        }

        if (request.getSeller() != null) {
            predicates.add(sellers.id.eq(request.getSeller()));
        }

        if (request.getSearch() != null) {
            String keyword = request.getSearch();
            predicates.add(
                    categories.categories.name.contains(keyword)
                            .or(tags.tags.name.contains(keyword))
                            .or(brands.name.contains(keyword))
                            .or(sellers.name.contains(keyword))
                            //.or(options.name.contains(keyword))
            );
        }

        return predicates;
    }
}
