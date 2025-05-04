package investLee.platform.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ReviewPermissionException extends RuntimeException {
    public ReviewPermissionException(String message) {
        super(message);
    }

    @ExceptionHandler(ReviewPermissionException.class)
    public ResponseEntity<String> handleReviewPermission(ReviewPermissionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}