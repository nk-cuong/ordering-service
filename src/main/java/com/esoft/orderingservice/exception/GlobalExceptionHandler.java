package com.esoft.orderingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.esoft.orderingservice.dto.BaseResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse(401, "The username or password is incorrect"));
    }

    @ExceptionHandler(JwtInvalidException.class)
    public ResponseEntity<BaseResponse> handleJwtInvalidException(JwtInvalidException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(401, "The JWT token is invalid"));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<BaseResponse> handleInsufficientAuthenticationException(
            InsufficientAuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new BaseResponse(403, "You are not authorized to access this resource"));
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<BaseResponse> handleMethodParamException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(400, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse(500, exception.getMessage()));
    }
}
