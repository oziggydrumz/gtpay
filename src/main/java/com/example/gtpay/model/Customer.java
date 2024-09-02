package com.example.gtpay.model;

import com.example.gtpay.dto.request.VerificationRequest;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table


public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private boolean enable=false;
    private String firstName;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String password;
    private Date date;
    @OneToOne
    private OtpValidation otpValidation;





}
