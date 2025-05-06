package minseok.cqrschallenge.review.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateResponse {
    private Long id;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
}