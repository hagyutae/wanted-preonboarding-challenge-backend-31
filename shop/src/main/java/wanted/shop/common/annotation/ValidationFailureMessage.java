package wanted.shop.common.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidationFailureMessage {
    String value();
}
