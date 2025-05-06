package lkdcode.wanted.ecommerce.framework.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@RequiredArgsConstructor
public enum ApplicationResponseCode {

    SUCCESS_OK("SUCCESS", OK, "리소스 요청에 성공한 경우"),

    FAIL("FAIL", BAD_REQUEST, "리소스 요청에 실패한 경우"),

    NOT_FOUND_PRODUCT("RESOURCE_NOT_FOUND", NOT_FOUND, "요청한 상품을 찾을 수 없습니다."),
    NOT_FOUND_DETAIL("RESOURCE_NOT_FOUND", NOT_FOUND, "요청한 상품의 상세 정보를 찾을 수 없습니다."),
    NOT_FOUND_PRICE("RESOURCE_NOT_FOUND", NOT_FOUND, "요청한 상품의 가격 정보를 찾을 수 없습니다."),
    NOT_FOUND_BRAND("INVALID_INPUT", BAD_REQUEST, "해당 브랜드를 찾을 수 없습니다."),
    NOT_FOUND_OPTION_GROUP("RESOURCE_NOT_FOUND", BAD_REQUEST, "해당 옵션 그룹을 찾을 수 없습니다."),
    NOT_FOUND_OPTION("RESOURCE_NOT_FOUND", BAD_REQUEST, "해당 옵션을 찾을 수 없습니다."),
    INVALID_BRAND("FORBIDDEN", FORBIDDEN, "브랜드가 일치하지 않습니다."),
    NOT_FOUND_CATEGORY("INVALID_INPUT", BAD_REQUEST, "해당 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_SELLER("INVALID_INPUT", BAD_REQUEST, "해당 판매자를 찾을 수 없습니다."),
    INVALID_SELLER("FORBIDDEN", FORBIDDEN, "해당 작업을 수행할 권한이 없습니다."),
    NOT_FOUND_TAG("INVALID_INPUT", BAD_REQUEST, "해당 태그를 찾을 수 없습니다."),
    INVALID_CATEGORY_PRIMARY("INVALID_INPUT", BAD_REQUEST, "대표 카테고리는 하나만 설정할 수 있습니다."),
    INVALID_IMAGE_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "이미지 순서는 연속된 숫자여야 합니다."),
    DUPLICATE_IMAGE_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "이미지 순서가 중복되었습니다."),

    INVALID_OPTION_GROUP_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "옵션 그룹 순서는 연속된 숫자여야 합니다."),
    DUPLICATE_OPTION_GROUP_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "옵션 그룹 순서가 중복되었습니다."),

    INVALID_OPTION_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "옵션은 순서는 연속된 숫자여야 합니다."),
    DUPLICATE_OPTION_DISPLAY_ORDER("INVALID_INPUT", BAD_REQUEST, "옵션 순서가 중복되었습니다."),

    DUPLICATE_PRODUCT_SLUG("INVALID_INPUT", BAD_REQUEST, "이미 존재하는 상품 슬러그입니다."),

    ;
    private final String code;
    private final HttpStatus httpStatus;
    private final String description;
}