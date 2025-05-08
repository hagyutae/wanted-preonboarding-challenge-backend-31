package cqrs.precourse.controller;

import cqrs.precourse.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @GetMapping
    public List<Void> getCategories() {
        return List.of();
    }

    @GetMapping("/{id}/products")
    public List<Product> getProducts(@PathVariable long id) {
        return List.of();
    }
}
