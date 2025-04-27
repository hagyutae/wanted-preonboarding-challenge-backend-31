import { Request, Response } from 'express';
import { productService } from '../services/product.service';
import { parsePaginationParams } from '../utils/pagination';
import { parseFilterParams, parseSortParams } from '../utils/product';
import { 
  createSuccessResponse, 
  createPaginatedResponse, 
  createErrorResponse, 
  ErrorCode 
} from '../utils/response';
import { 
  ProductListItemResponse, 
  ProductDetailResponse, 
  ProductCreateResponse,
  ProductUpdateResponse,
  ProductOptionModifyResponse,
  ProductImageAddResponse,
  ReviewListResponse,
  ReviewResponse,
  ApiResponse,
  PaginatedResponse
} from '../types';
import { Prisma } from '@prisma/client';

export const productController = {
  /**
   * 상품 목록 조회
   */
  async getProducts(req: Request, res: Response): Promise<Response<PaginatedResponse<ProductListItemResponse>>> {
    try {
      // 페이지네이션 파라미터 파싱
      const paginationParams = parsePaginationParams(req.query);
      
      // 필터링 파라미터 파싱
      const filterParams = parseFilterParams(req.query);
      
      // 정렬 파라미터 파싱
      const sortParams = parseSortParams(req.query);
      
      const result = await productService.getProducts(paginationParams, filterParams, sortParams);
      
      return res.json(
        createPaginatedResponse(
          result.products,
          result.total,
          result.page,
          result.perPage,
          "상품 목록을 성공적으로 조회했습니다."
        )
      );
    } catch (error) {
      console.error('상품 목록 조회 오류:', error);
      return res.status(500).json(
        createErrorResponse({
          code: ErrorCode.INTERNAL_ERROR,
          message: "상품 목록을 가져오는 중 오류가 발생했습니다."
        })
      );
    }
  },

  /**
   * 상품 상세 조회
   */
  async getProductById(req: Request, res: Response): Promise<Response<ApiResponse<ProductDetailResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const product = await productService.getProductById(productId);
      
      return res.json(
        createSuccessResponse(
          product,
          "상품 상세 정보를 성공적으로 조회했습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 상세 정보를 가져오는 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 상세 조회 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 등록
   */
  async createProduct(req: Request, res: Response): Promise<Response<ApiResponse<ProductCreateResponse>>> {
    try {
      const {
        name,
        slug,
        short_description,
        full_description,
        seller_id,
        brand_id,
        status,
        detail,
        price,
        categories,
        option_groups,
        images,
        tags
      } = req.body;

      // 필수 입력값 유효성 검사
      const validationErrors: Record<string, string> = {};
      
      if (!name) validationErrors.name = '상품명은 필수 항목입니다.';
      if (!slug) validationErrors.slug = '슬러그는 필수 항목입니다.';
      if (!seller_id) validationErrors.seller_id = '판매자 ID는 필수 항목입니다.';
      if (!brand_id) validationErrors.brand_id = '브랜드 ID는 필수 항목입니다.';
      if (!price || !price.base_price || price.base_price <= 0) {
        validationErrors.base_price = '기본 가격은 0보다 커야 합니다.';
      }
      
      if (Object.keys(validationErrors).length > 0) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "상품 등록에 실패했습니다.",
            details: validationErrors
          })
        );
      }

      // Prisma CreateInput 데이터 구성
      const productData: Prisma.ProductCreateInput = {
        name,
        slug,
        shortDescription: short_description,
        fullDescription: full_description,
        status: status || 'DRAFT',
        seller: {
          connect: { id: seller_id }
        },
        brand: {
          connect: { id: brand_id }
        },
        // 상품 상세 정보 생성
        detail: detail ? {
          create: {
            weight: detail.weight,
            dimensions: detail.dimensions ? {
              width: detail.dimensions.width,
              height: detail.dimensions.height,
              depth: detail.dimensions.depth
            } : undefined,
            materials: detail.materials,
            countryOfOrigin: detail.country_of_origin,
            warrantyInfo: detail.warranty_info,
            careInstructions: detail.care_instructions,
            additionalInfo: detail.additional_info
          }
        } : undefined,
        // 상품 가격 정보 생성
        price: price ? {
          create: {
            basePrice: price.base_price,
            salePrice: price.sale_price,
            costPrice: price.cost_price,
            currency: price.currency || 'KRW',
            taxRate: price.tax_rate
          }
        } : undefined,
        // 상품 카테고리 연결
        categories: categories && categories.length > 0 ? {
          create: categories.map((cat: any) => ({
            isPrimary: cat.is_primary || false,
            category: {
              connect: { id: cat.category_id }
            }
          }))
        } : undefined,
        // 상품 태그 연결
        tags: tags && tags.length > 0 ? {
          connect: tags.map((tagId: number) => ({ id: tagId }))
        } : undefined
      };

      // 상품 생성
      const product = await productService.createProduct(productData);

      // 상품 옵션 그룹 생성
      if (option_groups && option_groups.length > 0) {
        for (const group of option_groups) {
          await productService.createProductOptionGroup(product.id, {
            name: group.name,
            display_order: group.display_order,
            options: group.options
          });
        }
      }

      // 상품 이미지 등록 (별도 처리)
      if (images && images.length > 0) {
        for (const image of images) {
          // 이미지 등록 로직
          await productService.addProductImage(product.id, {
            url: image.url,
            altText: image.alt_text,
            isPrimary: image.is_primary || false,
            displayOrder: image.display_order || 0,
            product: {
              connect: { id: product.id }
            },
            option: image.option_id ? {
              connect: { id: image.option_id }
            } : undefined
          });
        }
      }

      // 응답 포맷에 맞게 변환
      const response: ProductCreateResponse = {
        id: product.id,
        name: product.name,
        slug: product.slug,
        created_at: product.created_at,
        updated_at: product.updated_at
      };
      
      return res.status(201).json(
        createSuccessResponse(
          response,
          "상품이 성공적으로 등록되었습니다."
        )
      );
    } catch (error) {
      console.error('상품 등록 오류:', error);
      
      const errorMessage = error instanceof Error ? error.message : '상품 등록에 실패했습니다.';
      
      return res.status(400).json(
        createErrorResponse({
          code: ErrorCode.INVALID_INPUT,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 수정
   */
  async updateProduct(req: Request, res: Response): Promise<Response<ApiResponse<ProductUpdateResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const {
        name,
        slug,
        short_description,
        full_description,
        seller_id,
        brand_id,
        status,
        detail,
        price,
        categories,
        option_groups,
        images,
        tags
      } = req.body;

      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }

      // Prisma UpdateInput 데이터 구성
      const updateData: Prisma.ProductUpdateInput = {};
      
      if (name) updateData.name = name;
      if (slug) updateData.slug = slug;
      if (short_description !== undefined) updateData.shortDescription = short_description;
      if (full_description !== undefined) updateData.fullDescription = full_description;
      if (status) updateData.status = status;
      
      if (seller_id) {
        updateData.seller = {
          connect: { id: seller_id }
        };
      }
      
      if (brand_id) {
        updateData.brand = {
          connect: { id: brand_id }
        };
      }
      
      // 상품 상세 정보 업데이트
      if (detail) {
        updateData.detail = {
          upsert: {
            create: {
              weight: detail.weight,
              dimensions: detail.dimensions ? {
                width: detail.dimensions.width,
                height: detail.dimensions.height,
                depth: detail.dimensions.depth
              } : undefined,
              materials: detail.materials,
              countryOfOrigin: detail.country_of_origin,
              warrantyInfo: detail.warranty_info,
              careInstructions: detail.care_instructions,
              additionalInfo: detail.additional_info
            },
            update: {
              weight: detail.weight,
              dimensions: detail.dimensions ? {
                width: detail.dimensions.width,
                height: detail.dimensions.height,
                depth: detail.dimensions.depth
              } : undefined,
              materials: detail.materials,
              countryOfOrigin: detail.country_of_origin,
              warrantyInfo: detail.warranty_info,
              careInstructions: detail.care_instructions,
              additionalInfo: detail.additional_info
            }
          }
        };
      }
      
      // 상품 가격 정보 업데이트
      if (price) {
        updateData.price = {
          upsert: {
            create: {
              basePrice: price.base_price,
              salePrice: price.sale_price,
              costPrice: price.cost_price,
              currency: price.currency || 'KRW',
              taxRate: price.tax_rate
            },
            update: {
              basePrice: price.base_price,
              salePrice: price.sale_price,
              costPrice: price.cost_price,
              currency: price.currency || 'KRW',
              taxRate: price.tax_rate
            }
          }
        };
      }

      // 상품 수정
      const updatedProduct = await productService.updateProduct(productId, updateData);
      
      // TODO: 카테고리, 태그, 옵션 그룹, 이미지는 별도 API로 처리

      // 응답 포맷에 맞게 변환
      const response: ProductUpdateResponse = {
        id: updatedProduct.id,
        name: updatedProduct.name,
        slug: updatedProduct.slug,
        updated_at: updatedProduct.updated_at
      };
      
      return res.json(
        createSuccessResponse(
          response,
          "상품이 성공적으로 수정되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 수정 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 수정 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 삭제
   */
  async deleteProduct(req: Request, res: Response): Promise<Response<ApiResponse<null>>> {
    try {
      const productId = parseInt(req.params.id);
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }

      // 상품 삭제
      await productService.deleteProduct(productId);
      
      return res.json(
        createSuccessResponse(
          null,
          "상품이 성공적으로 삭제되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 삭제 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 삭제 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 옵션 추가
   */
  async addProductOption(req: Request, res: Response): Promise<Response<ApiResponse<ProductOptionModifyResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const { 
        option_group_id,
        name,
        additional_price,
        sku,
        stock,
        display_order 
      } = req.body;
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      if (!option_group_id) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "옵션 그룹 ID는 필수 항목입니다."
          })
        );
      }
      
      if (!name) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "옵션명은 필수 항목입니다."
          })
        );
      }
      
      // Prisma CreateInput 데이터 구성
      const optionData: Prisma.ProductOptionCreateInput = {
        name,
        additionalPrice: additional_price || 0,
        sku,
        stock: stock || 0,
        displayOrder: display_order || 0,
        optionGroup: {
          connect: { id: option_group_id }
        }
      };
      
      // 상품 옵션 추가
      const option = await productService.addProductOption(productId, option_group_id, optionData);
      
      // 응답 포맷에 맞게 변환
      const response: ProductOptionModifyResponse = {
        id: option.id,
        option_group_id,
        name: option.name,
        additional_price: option.additional_price,
        sku: option.sku,
        stock: option.stock,
        display_order: option.display_order
      };
      
      return res.status(201).json(
        createSuccessResponse(
          response,
          "상품 옵션이 성공적으로 추가되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 옵션 추가 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 옵션 추가 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 옵션 수정
   */
  async updateProductOption(req: Request, res: Response): Promise<Response<ApiResponse<ProductOptionModifyResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const optionId = parseInt(req.params.optionId);
      const { 
        name,
        additional_price,
        sku,
        stock,
        display_order 
      } = req.body;
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      if (!optionId || isNaN(optionId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 옵션 ID입니다."
          })
        );
      }
      
      // Prisma UpdateInput 데이터 구성
      const updateData: Prisma.ProductOptionUpdateInput = {};
      
      if (name) updateData.name = name;
      if (additional_price !== undefined) updateData.additionalPrice = additional_price;
      if (sku !== undefined) updateData.sku = sku;
      if (stock !== undefined) updateData.stock = stock;
      if (display_order !== undefined) updateData.displayOrder = display_order;
      
      // 상품 옵션 수정
      const option = await productService.updateProductOption(productId, optionId, updateData);
      
      // 응답 포맷에 맞게 변환
      const response: ProductOptionModifyResponse = {
        id: option.id,
        option_group_id: option.option_group_id,
        name: option.name,
        additional_price: option.additional_price,
        sku: option.sku,
        stock: option.stock,
        display_order: option.display_order
      };
      
      return res.json(
        createSuccessResponse(
          response,
          "상품 옵션이 성공적으로 수정되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 옵션 수정 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 옵션 수정 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 옵션 삭제
   */
  async deleteProductOption(req: Request, res: Response): Promise<Response<ApiResponse<null>>> {
    try {
      const productId = parseInt(req.params.id);
      const optionId = parseInt(req.params.optionId);
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      if (!optionId || isNaN(optionId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 옵션 ID입니다."
          })
        );
      }
      
      // 상품 옵션 삭제
      await productService.deleteProductOption(productId, optionId);
      
      return res.json(
        createSuccessResponse(
          null,
          "상품 옵션이 성공적으로 삭제되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 옵션 삭제 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 옵션 삭제 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 이미지 추가
   */
  async addProductImage(req: Request, res: Response): Promise<Response<ApiResponse<ProductImageAddResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const { 
        url, 
        alt_text, 
        is_primary,
        display_order,
        option_id
      } = req.body;
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      if (!url) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "이미지 URL은 필수 항목입니다."
          })
        );
      }
      
      // Prisma CreateInput 데이터 구성
      const imageData: Prisma.ProductImageCreateInput = {
        url,
        altText: alt_text,
        isPrimary: is_primary || false,
        displayOrder: display_order || 0,
        product: {
          connect: { id: productId }
        }
      };
      
      // 옵션 ID가 제공되면 연결
      if (option_id) {
        imageData.option = {
          connect: { id: option_id }
        };
      }
      
      // 상품 이미지 추가
      const image = await productService.addProductImage(productId, imageData);
      
      // 응답 포맷에 맞게 변환
      const response: ProductImageAddResponse = {
        id: image.id,
        url: image.url,
        alt_text: image.alt_text,
        is_primary: image.is_primary,
        display_order: image.display_order,
        option_id: image.option_id
      };
      
      return res.status(201).json(
        createSuccessResponse(
          response,
          "상품 이미지가 성공적으로 추가되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 이미지 추가 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 이미지 추가 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 리뷰 조회
   */
  async getProductReviews(req: Request, res: Response): Promise<Response<ApiResponse<ReviewListResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const paginationParams = parsePaginationParams(req.query);
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      // 선택적 필터링: 평점
      let rating: number | undefined = undefined;
      if (req.query.rating) {
        rating = parseInt(req.query.rating as string);
        
        if (isNaN(rating) || rating < 1 || rating > 5) {
          return res.status(400).json(
            createErrorResponse({
              code: ErrorCode.INVALID_INPUT,
              message: "평점은 1-5 사이의 정수 값이어야 합니다."
            })
          );
        }
      }
      
      // 리뷰 조회 로직
      const { reviews, summary, total, page, perPage } = await productService.getProductReviews(
        productId,
        paginationParams,
        rating
      );
      
      const response: ReviewListResponse = {
        items: reviews,
        summary,
        pagination: {
          total_items: total,
          total_pages: Math.ceil(total / perPage),
          current_page: page,
          per_page: perPage
        }
      };
      
      return res.json(
        createSuccessResponse(
          response,
          "상품 리뷰를 성공적으로 조회했습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 리뷰 조회 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 리뷰 조회 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  },

  /**
   * 상품 리뷰 작성
   */
  async createProductReview(req: Request, res: Response): Promise<Response<ApiResponse<ReviewResponse>>> {
    try {
      const productId = parseInt(req.params.id);
      const { rating, title, content } = req.body;
      
      // 임시 변수: 실제 구현에서는 토큰에서 가져와야 함
      const userId = 1;
      
      // 유효성 검사
      if (!productId || isNaN(productId)) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "유효하지 않은 상품 ID입니다."
          })
        );
      }
      
      if (!rating || rating < 1 || rating > 5) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "평점은 1-5 사이의 값이어야 합니다."
          })
        );
      }
      
      if (!title) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "리뷰 제목은 필수 항목입니다."
          })
        );
      }
      
      if (!content) {
        return res.status(400).json(
          createErrorResponse({
            code: ErrorCode.INVALID_INPUT,
            message: "리뷰 내용은 필수 항목입니다."
          })
        );
      }
      
      // 리뷰 생성
      const review = await productService.createProductReview(productId, userId, {
        rating,
        title,
        content
      });
      
      return res.status(201).json(
        createSuccessResponse(
          review,
          "리뷰가 성공적으로 등록되었습니다."
        )
      );
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : '상품 리뷰 작성 중 오류가 발생했습니다.';
      const status = errorMessage === '상품을 찾을 수 없습니다.' ? 404 : 500;
      const errorCode = status === 404 ? ErrorCode.RESOURCE_NOT_FOUND : ErrorCode.INTERNAL_ERROR;
      
      console.error('상품 리뷰 작성 오류:', error);
      return res.status(status).json(
        createErrorResponse({
          code: errorCode,
          message: errorMessage
        })
      );
    }
  }
};