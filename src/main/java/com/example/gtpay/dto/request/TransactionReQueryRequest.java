package com.example.gtpay.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionReQueryRequest {
    @JsonProperty("transaction_reference")
    private String transactionReference;

    @JsonProperty("hash")
    private String hash;
}
