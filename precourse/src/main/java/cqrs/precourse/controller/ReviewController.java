package cqrs.precourse.controller;

import cqrs.precourse.domain.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    @GetMapping("/products/{id}/reviews")
    public List<Review> getReviews() {

        return null;
    }

    @PostMapping("/products/{id}/reviews")
    public void addReview() {

    }

    @PutMapping("/reviews/{id}")
    public void updateReview() {

    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable long id) {}
}
