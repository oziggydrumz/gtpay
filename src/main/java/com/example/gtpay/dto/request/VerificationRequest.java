package com.example.gtpay.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class VerificationRequest {


    @JsonProperty("hash")
    public String hash;

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("phoneNumber")
   private String phoneNumber;

    @JsonProperty("password")
   private String password;

}
