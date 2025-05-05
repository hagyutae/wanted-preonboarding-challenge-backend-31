package com.example.cqrsapp.main.service;

import com.example.cqrsapp.main.dto.MainResponse;
import com.example.cqrsapp.main.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainService {

    public final MainRepository mainRepository;

    @Transactional(readOnly = true)
    public MainResponse getMainPageContent() {
        return MainResponse.builder()
                .newProducts(mainRepository.findNewProducts())
                .popularProducts(mainRepository.findPopularProducts())
                .featuredCategories(mainRepository.findFeaturedCategories())
                .build();
    }
}
