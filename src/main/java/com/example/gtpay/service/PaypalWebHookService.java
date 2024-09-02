package com.example.gtpay.service;

import com.example.gtpay.dto.request.response.WebHookResponse;
import com.paypal.api.payments.Event;
import org.springframework.stereotype.Service;


public interface PaypalWebHookService {

    WebHookResponse processEvent(Event event);


}
