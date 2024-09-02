package com.example.gtpay.repository;

import com.example.gtpay.model.Customer;
import com.paypal.api.payments.Payee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findById(long customerId);
    
    Customer findByEmail(String email);

    Payee findByFirstName(String payerId);

    Customer findByPhoneNumber(String phoneNumber);


    Customer findByPassword(String password);
}
