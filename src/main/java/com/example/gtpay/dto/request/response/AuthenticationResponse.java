package com.example.gtpay.dto.request.response;

import com.paypal.orders.PaymentSource;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder


public class AuthenticationResponse {
    private String token;


}
