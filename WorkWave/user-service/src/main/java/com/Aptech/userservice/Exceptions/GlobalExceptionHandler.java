package com.Aptech.userservice.Exceptions;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Aptech.userservice.Dtos.Response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        return buildErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    @ExceptionHandler(value = ParseException.class)
    ResponseEntity<ApiResponse<Object>> handlingParseException(ParseException exception) {
        log.error("Exception: ", exception);
        return buildErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handlingAppException(AppException exception) {
        return buildErrorResponse(exception.getErrorCode());
    }

    @ExceptionHandler(value = JpaSystemException.class)
    ResponseEntity<ApiResponse<Object>> handlingJpaSystemException(JpaSystemException exception) {
        log.error("Exception: ", exception);
        if (exception.getMessage() != null && exception.getMessage().contains("User already has this role")) {
            return buildErrorResponse(ErrorCode.USER_ALREADY_HAS_THIS_ROLE);
        }
        return buildErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    private ResponseEntity<ApiResponse<Object>> buildErrorResponse(ErrorCode errorCode) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status("fail")
                .message(errorCode.getMessage())
                .errorCode(String.valueOf(errorCode.getCode()))
                .build();

        return ResponseEntity.status(errorCode.getStatusCode().value()).body(apiResponse);
    }
}
