package com.example.gtpay.service.serviceImp;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class CustomerAlReadyExist extends Throwable {
    private String message ;
    private HttpStatus httpStatus;

    @Override
    public String getMessage(){
        return message;
    }
}