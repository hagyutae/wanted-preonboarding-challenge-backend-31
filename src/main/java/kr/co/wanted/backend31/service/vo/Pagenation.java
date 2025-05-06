package kr.co.wanted.backend31.service.vo;

import lombok.Builder;

@Builder
public record Pagenation(long totalItems, int totalPages, int currentPage, int perPage)  {

}
