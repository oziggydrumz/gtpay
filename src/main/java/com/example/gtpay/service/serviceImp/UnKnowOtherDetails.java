package com.example.gtpay.service.serviceImp;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class UnKnowOtherDetails extends Throwable {
    private String message;
    private HttpStatus httpStatus;

    @Override
    public String getMessage(){
        return message;
    }
}
