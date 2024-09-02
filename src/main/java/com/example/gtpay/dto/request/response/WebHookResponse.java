package com.example.gtpay.dto.request.response;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class WebHookResponse {
    public BigDecimal amount;
    private String reference;
    private String transReference;
    private String paymentReference;
    private String statusCode;
    private String responseDesc;
    private String hashing;
}
