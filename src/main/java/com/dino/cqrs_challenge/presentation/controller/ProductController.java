package com.dino.cqrs_challenge.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {


    @GetMapping("/products")
    public String products() {
        return "Product List";
    }

}
