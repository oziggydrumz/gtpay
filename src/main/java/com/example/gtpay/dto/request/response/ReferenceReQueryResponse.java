package com.example.gtpay.dto.request.response;

import lombok.Data;

import java.util.Date;

@Data

public class ReferenceReQueryResponse {
    private String responseCode;
    private String responseDesc;
    private Date date;
    private String time;
    private String transactionReference;
    private String customerName;
    private String amount;
    private String merchantName;
    private String transactionStatus;
    private String transactionMessage;
    private String hash;


}
