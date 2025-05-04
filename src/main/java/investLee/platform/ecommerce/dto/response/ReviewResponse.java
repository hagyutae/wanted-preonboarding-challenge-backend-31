package investLee.platform.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private int rating;
    private String title;
    private String content;
    private boolean verifiedPurchase;
    private int helpfulVotes;
    private LocalDateTime createdAt;
    private Long userId;
}