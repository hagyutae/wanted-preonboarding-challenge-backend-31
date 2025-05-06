package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value;

public record ParamCondition<T>(
    String key,
    T value
) {

    public static <T> ParamCondition<T> of(String key, T value) {
        return new ParamCondition<>(key, value);
    }

    public boolean keyNotEmpty() {
        return key != null && !key.trim().isEmpty();
    }

    public boolean valueNotEmpty() {
        return value != null && !valueAsStringToUpperCase().isEmpty();
    }

    public String valueAsStringToUpperCase() {
        return String.valueOf(value).trim().toUpperCase();
    }

    public String keyToUpperCase() {
        return key.trim().toUpperCase();
    }
}