package com.example.gtpay.dto.request.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class InitializePaymentResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("Data")
    private Data data;


    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)

    public class Data{
       @JsonProperty("authorization_url")
       private String authorizationUrl;

       @JsonProperty("access_code")
        private String accessCode;

       @JsonProperty("reference")
        private String reference;


    }
}
