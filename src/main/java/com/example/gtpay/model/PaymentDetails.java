package com.example.gtpay.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table

public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String customerName;
    private String referenceId;
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @JoinColumn(name = "gateway_response")
    private String gatewayResponse;


    @JoinColumn(name="payed_at")
    private String payedAt;



     @JoinColumn(name="upDate_at")
    private String upDatedAt;

     @JoinColumn(name="channel")
     private String channel;

     @JoinColumn(name = "currency")
     private String currency;

     @JoinColumn(name="ip_address")
     private String ipAddress;

     private String SuccessUrl;

     private String cancelUrl;



    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    private Date createdOn;
}
