package com.example.gtpay.dto.request;

import lombok.Data;

import java.util.Date;

@Data

public class ReQueryRequest {

    private String TransactionReference;

    private Date date;
}
