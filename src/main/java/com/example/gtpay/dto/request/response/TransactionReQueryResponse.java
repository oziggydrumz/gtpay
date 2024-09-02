package com.example.gtpay.dto.request.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionReQueryResponse {
    private String transReference;
    private String referenceId;
    private String paymentReference;
    private String responseConde;
    private String ResponseDesc;
    private String hash;
}
