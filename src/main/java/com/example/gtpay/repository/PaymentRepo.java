package com.example.gtpay.repository;

import com.example.gtpay.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentDetails,Long> {
    PaymentDetails findByReferenceId(String referenceId);


}
