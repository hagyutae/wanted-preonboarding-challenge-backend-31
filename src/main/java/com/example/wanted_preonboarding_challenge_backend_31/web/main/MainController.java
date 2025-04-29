package com.example.wanted_preonboarding_challenge_backend_31.web.main;

import static com.example.wanted_preonboarding_challenge_backend_31.web.main.dto.MainSuccessType.MAIN_SEARCH;

import com.example.wanted_preonboarding_challenge_backend_31.application.main.MainService;
import com.example.wanted_preonboarding_challenge_backend_31.common.dto.SuccessRes;
import com.example.wanted_preonboarding_challenge_backend_31.web.main.dto.response.MainSearchRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    public SuccessRes<MainSearchRes> search() {
        return SuccessRes.of(MAIN_SEARCH, mainService.search());
    }
}
