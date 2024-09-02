package com.example.gtpay.repository;

import com.example.gtpay.model.OtpValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpValidationRepo extends JpaRepository<OtpValidation,Long> {

    OtpValidation findByToken(String token);

}
