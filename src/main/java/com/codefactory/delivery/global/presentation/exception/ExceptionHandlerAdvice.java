package com.codefactory.delivery.global.presentation.exception;

import com.codefactory.delivery.global.infrastructure.message.MessageUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice("com.codefactory.delivery")
public class ExceptionHandlerAdvice {
    private final MessageUtils messageUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handler(Exception e) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if (e instanceof HttpStatusCodeException statusCodeException) {
            status = statusCodeException.getStatusCode();

            // 직접 정의한 예외인 경우
            if (e instanceof BadRequestException badRequestException && StringUtils.hasText(badRequestException.getField())) {
                message = Map.of(badRequestException.getField(), message);
            }

        } else if (e instanceof MethodArgumentNotValidException validException) {
            status = HttpStatus.BAD_REQUEST;
            message = messageUtils.getErrorMessages(validException.getBindingResult());

        } else if (e instanceof ConstraintViolationException constraintViolationException) {
            status = HttpStatus.BAD_REQUEST;
            message = messageUtils.getConstraintValidationMessages(constraintViolationException);

        } else if (e instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
            message = messageUtils.getMessage("MISSING.BODY");

        } else if (e instanceof AuthorizationDeniedException) {
            status = HttpStatus.UNAUTHORIZED;
        }

        log.error("HTTP ERROR", e);

        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }
}