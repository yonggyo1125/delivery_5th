package com.codefactory.delivery.global.infrastructure.message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageUtils {
    private final MessageSource messageSource;
    private final HttpServletRequest request;

    public String getMessage(String code) {
        return getMessage(code, null, null);
    }

    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, null);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false);

        try {
            Locale locale = request.getLocale();
            defaultMessage = StringUtils.hasText(defaultMessage) ? defaultMessage : "";

            return ms.getMessage(code, args, defaultMessage, locale);
        } catch (Exception e) {
            return "";
        } finally {
            ms.setUseCodeAsDefaultMessage(true);
        }
    }

    public List<String> getMessages(List<String> codes) {
        return codes == null
                ? null
                : codes.stream().map(this::getMessage)
                .filter(StringUtils::hasText)
                .toList();
    }

    public List<String> getMessages(String[] codes) {
        return codes == null
                ? null
                : getMessages(Arrays.asList(codes));
    }

    public List<String> getMessages(FieldError fieldError) {
        List<String> messages = getMessages(fieldError.getCodes());

        if (messages == null || messages.isEmpty()) {
            String defaultMessage = StringUtils.hasText(fieldError.getDefaultMessage()) ? fieldError.getDefaultMessage() : "";
            messages = List.of(defaultMessage);
        }

        return messages;
    }

    public Map<String, List<String>> getConstraintValidationMessages(ConstraintViolationException ex) {
        if (ex == null) return Map.of();
        return ex.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(
                        v -> extractFieldName(v.getPropertyPath().toString()),
                        LinkedHashMap::new,
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));
    }

    public Map<String, List<String>> getErrorMessages(Errors errors) {
        Map<String, List<String>> validationErrors = errors.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        this::getMessages,
                        (v1, v2) -> v2
                ));

        List<String> globalErrors = errors.getGlobalErrors().stream()
                .flatMap(o -> getMessages(o.getCodes())
                        .stream()).toList();

        if (!globalErrors.isEmpty()) {
            validationErrors.put("global", globalErrors);
        }

        return validationErrors;
    }

    // propertyPath에서 마지막 요소(실제 필드명) 추출
    private static String extractFieldName(String propertyPath) {
        if (propertyPath == null || propertyPath.isBlank()) return propertyPath;
        // 예: "create.arg0.email" or "arg0.email" or "email"
        String[] parts = propertyPath.split("\\.");
        return parts[parts.length - 1];
    }
}