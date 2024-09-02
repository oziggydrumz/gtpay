package com.example.gtpay.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data


public class InitializePaymentRequest {

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("emailAddress")
    private String emailAddress;

    @JsonProperty("currency")
    private String currency;

     @JsonProperty("channel")
    private String channel;



}
