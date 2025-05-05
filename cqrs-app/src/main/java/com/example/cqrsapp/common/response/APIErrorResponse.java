package com.example.cqrsapp.common.response;

import com.example.cqrsapp.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Builder
public class APIErrorResponse<T> {

    private boolean success;
    private Error<T> error;

    public static APIErrorResponse<Void> fail(ErrorCode errorCode) {
        return APIErrorResponse.<Void>builder()
                .success(false)
                .error(Error.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getErrorMsg())
                        .details(List.of())
                        .build())
                .build();
    }

    public static APIErrorResponse<BindingResultDetails> fail(ErrorCode errorCode, BindingResult bindingResult) {
        return APIErrorResponse.<BindingResultDetails>builder()
                .success(false)
                .error(Error.<BindingResultDetails>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getErrorMsg())
                        .details(BindingResultDetails.of(bindingResult))
                        .build())
                .build();
    }

    public static APIErrorResponse<ResourceNotFoundDetails> fail(ErrorCode errorCode, ResourceNotFoundDetails details) {
        return APIErrorResponse.<ResourceNotFoundDetails>builder()
                .success(false)
                .error(Error.<ResourceNotFoundDetails>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getErrorMsg())
                        .details(List.of(details))
                        .build())
                .build();
    }

    public static APIErrorResponse<ConflictDetails> fail(ErrorCode errorCode, ConflictDetails details) {
        return APIErrorResponse.<ConflictDetails>builder()
                .success(false)
                .error(Error.<ConflictDetails>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getErrorMsg())
                        .details(List.of(details))
                        .build())
                .build();
    }


    @Builder
    @Getter
    public static class Error<T> {
        private final String code;
        private final String message;
        private final List<T> details;
    }


    @Builder
    @Getter
    public static class BindingResultDetails {
        private final String field;
        private final String value;
        private final String reason;

        private BindingResultDetails(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<BindingResultDetails> of(String field, String value, String reason) {
            List<BindingResultDetails> details = new ArrayList<>();
            details.add(new BindingResultDetails(field, value, reason));
            return details;
        }

        public static List<BindingResultDetails> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new BindingResultDetails(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }

    @Getter
    @Builder
    public static class ResourceNotFoundDetails {
        private final String resourceType;
        private final String resourceId;
    }

    @Getter
    @Builder
    public static class ConflictDetails {
        private final String field;
        private final String value;
        private final String detailMessage;
    }
}

