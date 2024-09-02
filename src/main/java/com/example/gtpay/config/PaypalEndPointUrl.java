package com.example.gtpay.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class PaypalEndPointUrl extends Throwable {
    private String message;
    private HttpStatus httpStatus;

    @Override
    public String getMessage(){
        return message;
    }
}
