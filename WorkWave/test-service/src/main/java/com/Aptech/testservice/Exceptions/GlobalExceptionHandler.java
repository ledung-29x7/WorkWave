package com.Aptech.testservice.Exceptions;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Aptech.testservice.Dtos.Responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // @ExceptionHandler(value = RuntimeException.class)
    // ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException
    // exception) {
    // log.error("Exception: ", exception);
    // ApiResponse apiResponse = new ApiResponse();

    // apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    // apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    // return ResponseEntity.badRequest().body(apiResponse);
    // }

    // @ExceptionHandler(value = ParseException.class)
    // ResponseEntity<ApiResponse> handlingParseException(ParseException exception)
    // {
    // log.error("Exception: ", exception);
    // ApiResponse apiResponse = new ApiResponse();

    // apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    // apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    // return ResponseEntity.badRequest().body(apiResponse);
    // }

    // @ExceptionHandler(value = AppException.class)
    // ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
    // ErrorCode errorCode = exception.getErrorCode();
    // ApiResponse apiResponse = new ApiResponse();

    // apiResponse.setCode(errorCode.getCode());
    // apiResponse.setMessage(errorCode.getMessage());

    // return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    // }

    // // @ExceptionHandler(value = AuthorizationDeniedException.class)
    // // ResponseEntity<ApiResponse>
    // // handlingAccessDeniedException(AuthorizationDeniedException exception) {
    // // ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    // // return ResponseEntity.status(errorCode.getStatusCode())
    // // .body(ApiResponse.builder()
    // // .code(errorCode.getCode())
    // // .message(errorCode.getMessage())
    // // .build());
    // // }

    // @ExceptionHandler(value = JpaSystemException.class)
    // ResponseEntity<ApiResponse> handlingJpaSystemException(JpaSystemException
    // exception) {
    // if (exception.getMessage().contains("User already has this role")) {
    // ErrorCode errorCode = ErrorCode.USER_ALREADY_HAS_THIS_ROLE;
    // return ResponseEntity.status(errorCode.getStatusCode())
    // .body(ApiResponse.builder()
    // .code(errorCode.getCode())
    // .message(errorCode.getMessage())
    // .build());
    // }
    // ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
    // return ResponseEntity.status(errorCode.getStatusCode())
    // .body(ApiResponse.builder()
    // .code(errorCode.getCode())
    // .message(errorCode.getMessage())
    // .build());

    // }
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
