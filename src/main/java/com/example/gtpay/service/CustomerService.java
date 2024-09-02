package com.example.gtpay.service;


import com.example.gtpay.dto.request.CustomerRegisterRequest;
import com.example.gtpay.dto.request.AuthenticationRequest;
import com.example.gtpay.dto.request.OtpValidationRequest;
import com.example.gtpay.dto.request.response.AuthenticationResponse;
import com.example.gtpay.model.Customer;
import com.example.gtpay.service.serviceImp.CustomerAlReadyExist;
import com.example.gtpay.service.serviceImp.CustomerDoesNotExist;
import com.example.gtpay.service.serviceImp.OtpValidationCanNotBeNull;
import com.example.gtpay.service.serviceImp.AccountNotConfirm;
import org.springframework.stereotype.Service;

@Service

public interface CustomerService  {
    AuthenticationResponse customerRegistration(CustomerRegisterRequest request) throws CustomerAlReadyExist;

    String confirmation(OtpValidationRequest request) throws OtpValidationCanNotBeNull, AccountNotConfirm;

    AuthenticationResponse login(AuthenticationRequest loginRequest);

    Customer findCustomer(String email) throws CustomerDoesNotExist;




}
