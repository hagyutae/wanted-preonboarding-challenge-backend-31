package kr.co.wanted.backend31.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Positive;
import kr.co.wanted.backend31.controller.vo.SuccessResponse;
import kr.co.wanted.backend31.service.ProductSearchService;
import kr.co.wanted.backend31.service.vo.ProductSearchCondition;
import kr.co.wanted.backend31.service.vo.ProductSearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductSearchController {
        private final ProductSearchService searchService;

        @GetMapping("")
        public ResponseEntity<SuccessResponse<ProductSearchResult>> searchPage(
                        @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
                        @Positive @RequestParam(required = false, defaultValue = "10") Integer perPage,
                        @RequestParam(required = false, defaultValue = "created_at:desc") List<String> sort,
                        @RequestParam(required = false) String status,
                        @RequestParam(required = false) BigDecimal minPrice,
                        @RequestParam(required = false) BigDecimal maxPrice,
                        @RequestParam(required = false) List<Long> category,
                        @RequestParam(required = false) Long seller, @RequestParam(required = false) Long brand,
                        @RequestParam(required = false) Boolean inStock,
                        @RequestParam(required = false) String search) {
                log.info(
                                "GET /products: page: {}, perPage: {}, sort: {}, status: {}, minPrice: {}, maxPrice: {}, category: {}, seller: {}, brand: {}, inStock: {}, search: {}",
                                page, perPage, sort, status, minPrice, maxPrice, category, seller, brand, inStock,
                                search);

                var pageable = PageRequest.of(page - 1, perPage);
                final Function<String, String> snakeToCamel = property -> Pattern.compile("_([a-z])")
                                .matcher(property)
                                .replaceAll(m -> m.group(1).toUpperCase());

                final var sortConditions = sort.stream()
                                .map(cond -> Arrays.asList(cond.split(":")))
                                .collect(Collectors.toMap(x -> x.get(0), x -> x.get(1)));

                final var ascSortProperties = sortConditions.entrySet().stream()
                                .filter(e -> "asc".equals(e.getValue()))
                                .map(Map.Entry::getKey)
                                .map(snakeToCamel)
                                .toList();
                if (!ascSortProperties.isEmpty()) {
                        pageable = pageable.withSort(Sort.Direction.ASC, ascSortProperties.toArray(new String[0]));
                }
                final var descSortProperties = sortConditions.entrySet().stream()
                                .filter(e -> "desc".equals(e.getValue()))
                                .map(Map.Entry::getKey)
                                .map(snakeToCamel)
                                .toList();
                if (!descSortProperties.isEmpty()) {
                        pageable = pageable.withSort(Sort.Direction.DESC, descSortProperties.toArray(new String[0]));
                }

                final var cond = ProductSearchCondition.builder()
                                .pageable(pageable)
                                .status(Optional.ofNullable(status))
                                .minPrice(Optional.ofNullable(minPrice))
                                .maxPrice(Optional.ofNullable(maxPrice))
                                .categories(Optional.ofNullable(category).orElse(List.of()))
                                .seller(Optional.ofNullable(seller))
                                .brand(Optional.ofNullable(brand))
                                .inStock(Optional.ofNullable(inStock))
                                .search(Optional.ofNullable(search))
                                .build();

                final var resp = searchService.searchPage(cond);

                return ResponseEntity.ok()
                                .body(SuccessResponse.of(Optional.of(resp)));
        }

}
