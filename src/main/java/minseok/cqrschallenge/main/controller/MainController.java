package minseok.cqrschallenge.main.controller;

import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.dto.ApiResponse;
import minseok.cqrschallenge.main.dto.response.MainPageResponse;
import minseok.cqrschallenge.main.service.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {
    
    private final MainService mainService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<MainPageResponse>> getMainPageData() {
        MainPageResponse response = mainService.getMainPageData();
        
        return ResponseEntity.ok(ApiResponse.success(
                response, 
                "메인 페이지 상품 목록을 성공적으로 조회했습니다."
        ));
    }
}