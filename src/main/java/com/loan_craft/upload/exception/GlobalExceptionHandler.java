package com.loan_craft.upload.exception;


import com.loan_craft.upload.dto.response.ApiGenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiGenericResponse<Object>> handleUserAlreadyExists(CustomException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiGenericResponse<>(false, null, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiGenericResponse<Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiGenericResponse<>(false, "", "Something went wrong", null));
    }

}
