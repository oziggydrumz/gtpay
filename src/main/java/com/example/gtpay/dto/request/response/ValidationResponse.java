package com.example.gtpay.dto.request.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponse {

        private String referenceID;
        private String customerName;
        private String statusCode;
        private String statusMessage;
        private BigDecimal totalAmount;
        private int currency;
        private String hash;
        private String token;

        // Constructor, getters, and setters
    }



