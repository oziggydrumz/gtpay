package com.example.gtpay.dto.request.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReQueryResponse {

    private String responseCode;
    private String responseDesc;
    private Date date;
    private String time;
    private BigDecimal amount;
    private String transactionReference;
    private String customerName;
    private String customerId;
    private String transactionMessage;
    private String transactionStatus;
    private String Hashing;


}
