package com.pawn.wantedcqrs.common.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/ping")
    private ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}
