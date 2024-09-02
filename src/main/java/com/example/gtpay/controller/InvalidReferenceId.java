package com.example.gtpay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class InvalidReferenceId extends Throwable {
    private String message;
    private HttpStatus httpStatus;

    @Override
    public String getMessage(){
        return this.message;
    }
}
