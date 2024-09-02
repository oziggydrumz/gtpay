package com.example.gtpay.dto.request.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

@NoArgsConstructor

public class TransactionReQueryAfterPaymentResponse {

    private String responseCode;
    private String ResponseDesc;
    private String transDate;
    private String transTime;
    private String transReference;
    private BigDecimal amount;
    private String customerName;
    private String merchantName;
    private String transactionStatus;
    private String transactionMessage;
    private String hash;
}
