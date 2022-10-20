package com.example.spring_subject.controller;

import com.example.spring_subject.global.dto.GlobalRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = Exception.class)
    public GlobalRequestDto customExceptionHandler(Exception ex) {
        return new GlobalRequestDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value () );
    }
}