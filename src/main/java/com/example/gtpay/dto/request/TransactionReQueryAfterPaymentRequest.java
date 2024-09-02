package com.example.gtpay.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder

public class TransactionReQueryAfterPaymentRequest {
    private String referenceId;
    private String customerId;
    private String formId;
    private String fieldId;
    private Date date;
    private String hash;
}
