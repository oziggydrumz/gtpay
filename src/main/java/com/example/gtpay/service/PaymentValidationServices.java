package com.example.gtpay.service;

import com.example.gtpay.dto.request.*;
import com.example.gtpay.dto.request.response.*;
import com.example.gtpay.service.serviceImp.*;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentValidationServices {
    ReferenceReQueryResponse reQueryWithReference(ReferenceReQueryRequest request);

    ReQueryResponse transactionReQuery(ReQueryRequest requeryRequest) throws UnKnowOtherDetails;

    TransactionReQueryAfterPaymentResponse reQuery(TransactionReQueryAfterPaymentRequest AfterPaymentRequest) throws UnKnowTransactionReference;




   TransactionReQueryResponse transactionReQuery(String transactionReference) throws UnKnowOtherDetails, UnKnowReferenceNumber;



    ValidationResponse validate(VerificationRequest request) throws UnKnowOtherDetails, InvalidCurrency;

    Object paymentUpdate(PaymentUpDateRequest paymentUpDateRequest) throws InvalidRequest, BadHashing, PayPalRESTException;




}
