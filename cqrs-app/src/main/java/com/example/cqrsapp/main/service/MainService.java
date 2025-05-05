package com.example.cqrsapp.main.service;

import com.example.cqrsapp.main.dto.MainResponse;
import com.example.cqrsapp.main.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

    public final MainRepository mainRepository;

    public MainResponse getMainPageContent() {
        return MainResponse.builder()
                .newProducts(mainRepository.findNewProducts())
                .popularProducts(mainRepository.findPopularProducts())
                .featuredCategories(mainRepository.findFeaturedCategories())
                .build();
    }
}
