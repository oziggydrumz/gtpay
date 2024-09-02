package com.example.gtpay.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentUpDateRequest {
    private  String paymentId;

    private String payerId;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("payment_reference")
    private String paymentReference;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("currency")
    private int currency;

    @JsonProperty("phoneNumber")
   private String phoneNumber;

    @JsonProperty("password")
    private String password;

    @JsonProperty("hashing")
    private String hash;

    private String cancelUrl;
    private String successUrl;


}
