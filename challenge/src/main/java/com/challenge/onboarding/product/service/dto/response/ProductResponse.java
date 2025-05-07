package com.challenge.onboarding.product.service.dto.response;

public record ProductResponse(
        Long id,
        String name,
        String slug,
        String shortDescription,
        int basePrice,
        int salePrice,
        String currency,
        Image primaryImage,
        Brand brand,
        Seller seller,
        double rating,
        int reviewCount,
        boolean inStock,
        String status,
        String createdAt
) {
    public record Image(String url, String altText) {
    }

    public record Brand(int id, String name) {
    }

    public record Seller(int id, String name) {
    }
}
