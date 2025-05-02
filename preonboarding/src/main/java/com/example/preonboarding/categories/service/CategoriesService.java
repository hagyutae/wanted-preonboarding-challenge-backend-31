package com.example.preonboarding.categories.service;

import com.example.preonboarding.categories.domain.Categories;
import com.example.preonboarding.categories.repository.CategoriesRepository;
import com.example.preonboarding.categories.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    public List<CategoriesResponse> findCategories(Integer level){
        List<Categories> findAll = categoriesRepository.findAll();

        Map<Long, List<Categories>> hasParent  = findAll.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(Categories::getParentId));


        List<Categories> byLevel = findAll.stream()
                .filter(c -> c.getLevel().equals(level))
                .collect(Collectors.toList());

        return byLevel.stream().map(i -> getCategoriesResponse(i,hasParent))
                .collect(Collectors.toList());
    }

    private CategoriesResponse getCategoriesResponse(Categories i, Map<Long, List<Categories>> parentMap) {
        CategoriesResponse category = CategoriesResponse.builder()
                .id(i.getId())
                .name(i.getName())
                .slug(i.getSlug())
                .level(i.getLevel())
                .description(i.getDescription())
                .imageUrl(i.getImageUrl())
                .build();

        List<Categories> children = parentMap.get(category.getId());

        if(children != null && !children.isEmpty()){
            List<CategoriesResponse> list = children.stream().map(
                    c-> getCategoriesResponse(c,parentMap)
            ).collect(Collectors.toList());
            category.setChildren(list);
        }
        return category;
    }
}
