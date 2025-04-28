package com.mkhwang.wantedcqrs.product.infra.impl;

import com.mkhwang.wantedcqrs.product.domain.QCategory;
import com.mkhwang.wantedcqrs.product.domain.QProductCategory;
import com.mkhwang.wantedcqrs.product.domain.dto.main.MainCategoryDto;
import com.mkhwang.wantedcqrs.product.domain.dto.main.MainResponseDto;
import com.mkhwang.wantedcqrs.product.infra.MainSearchRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MainSearchRepositoryImpl implements MainSearchRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final QCategory qCategory = QCategory.category;
  private final QProductCategory qProductCategory = QProductCategory.productCategory;

  @Override
  public MainResponseDto searchMainPage() {
    List<MainCategoryDto> categoryList = jpaQueryFactory.select(Projections.constructor(
                    MainCategoryDto.class,
                    qCategory.id,
                    qCategory.name,
                    qCategory.slug,
                    qCategory.level,
                    qCategory.imageUrl,
                    qCategory.parent.id,
                    qProductCategory.id.count()
            ))
            .from(qCategory)
            .leftJoin(qProductCategory)
            .on(qProductCategory.category.id.eq(qCategory.id))
            .groupBy(qCategory.id)
            .orderBy(qCategory.level.asc())
            .fetch();

    Map<Long, MainCategoryDto> categoryMap = categoryList.stream()
            .collect(Collectors.toMap(MainCategoryDto::getId, dto -> dto));

    for (MainCategoryDto dto : categoryList) {
      if (dto.getLevel() > 1 && dto.getParent() != null) {
        MainCategoryDto parent = categoryMap.get(dto.getParent());
        while (parent != null) {
          parent.addCount(dto.getProductCount());
          if (parent.getLevel() == 1) {
            break;
          }
          parent = categoryMap.get(parent.getParent());
        }
      }
    }

    return MainResponseDto.of(
            List.of(), List.of(),
            categoryMap.values().stream().filter(c -> c.getLevel() == 1).collect(Collectors.toList())
    );
  }
}
