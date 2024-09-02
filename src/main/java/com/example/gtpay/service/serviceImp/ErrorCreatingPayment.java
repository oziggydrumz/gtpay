package com.example.gtpay.service.serviceImp;

import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorCreatingPayment extends Throwable {
   private String message;
   private PayPalRESTException e;

   @Override
    public String getMessage(){
       return message;
   }
}
