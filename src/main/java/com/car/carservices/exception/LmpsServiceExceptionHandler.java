package com.car.carservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class LmpsServiceExceptionHandler {

    @ExceptionHandler(LmpsServiceDuplicateException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(LmpsServiceDuplicateException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage()); // "Duplicate values are not allowed"
        body.put("status", 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
