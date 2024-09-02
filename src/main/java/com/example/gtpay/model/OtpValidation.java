package com.example.gtpay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
@Entity
@Table

public class OtpValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "token")
    private String token;

    @OneToOne (fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    @JoinColumn(name = "customer")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "payment")
    private PaymentDetails payment;

    private LocalDateTime expire;

    private LocalDateTime confirm;

}
