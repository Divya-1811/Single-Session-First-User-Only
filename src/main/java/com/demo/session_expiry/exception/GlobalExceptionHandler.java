package com.demo.session_expiry.exception;

import com.demo.session_expiry.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex){
        return ResponseEntity.internalServerError().body(CommonUtil.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> customValidationException(CustomException ex){
        return ResponseEntity.badRequest().body(CommonUtil.getApiResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(CommonUtil.getApiResponse(HttpStatus.FORBIDDEN.value(),ex.getMessage()));
    }
}
