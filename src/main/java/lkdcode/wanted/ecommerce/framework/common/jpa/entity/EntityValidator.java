package lkdcode.wanted.ecommerce.framework.common.jpa.entity;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class EntityValidator<T> {

    @SuppressWarnings("resource")
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @SuppressWarnings("unchecked")
    protected void validSelf() {
        final var violations = VALIDATOR.validate((T) this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
