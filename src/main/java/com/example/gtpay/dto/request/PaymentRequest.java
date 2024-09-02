package com.example.gtpay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private double totalAmount;
    private String paymentType;
    private String currency;
    private String intent;
    private String cancelUrl;
    private String successUrl;
}
