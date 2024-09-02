package com.example.gtpay.repository;

import com.example.gtpay.model.TransactionMode;
import com.paypal.api.payments.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TransactionReferenceRepo extends JpaRepository<TransactionMode,Long> {
    Optional<TransactionMode> findByTransactionReference(String transReference);
    Optional <TransactionMode> findByPaypalTransactionId(String paypalTransactionId);


    TransactionMode findByTransactionReferenceAndDate(String transactionReference, Date date);

   Optional <TransactionMode> findByReference(String saleId);

}
