package com.example.gtpay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder

public class TransactionMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String customerName;
    private String transactionReference;
    private String paypalTransactionId;
    private String transDescription;
    private String transactionStatusCode;
    private Date date;
    private String time;
    private String description;
    private BigDecimal amount;
    private String transactionMessage;
    private String responseDesc;
    private String paymentReference;
    private  String reference;
    private String responseCode;

}
