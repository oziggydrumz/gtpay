package com.example.gtpay.service.serviceImp;

import com.example.gtpay.RestTemplateConfig;
import com.example.gtpay.dto.request.*;
import com.example.gtpay.dto.request.response.*;
import com.example.gtpay.model.Customer;
import com.example.gtpay.model.TransactionMode;
import com.example.gtpay.repository.CustomerRepo;
import com.example.gtpay.repository.TransactionReferenceRepo;
import com.example.gtpay.service.PaymentValidationServices;
import com.example.gtpay.service.PaypalWebHookService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PaymentValidationServiceImp implements PaymentValidationServices {

    private static final String hashKey = "834532DGWER6567RTYHS";
    @Autowired
    private CustomerRepo repo;

    PaymentServiceImp serviceImp;


    @Autowired
    private CalculateHashing calculateHashes;

    @Autowired
    private RestTemplateConfig template;


    private PaymentServiceImp paymentServiceImp;
    private PaypalService paypalService;

    private PaypalWebHookService paypalWebHookService;

    private TransactionReferenceRepo transactionRepo;

    private String paypal_endPoint = "https://api.paypal.com/v1/payments/payment";

    private String clint_id;

    private String clint_secrete;

    @Override
    public ReferenceReQueryResponse reQueryWithReference(ReferenceReQueryRequest request) {


        return null;
    }

    @Override
    public ReQueryResponse transactionReQuery(ReQueryRequest request) throws UnKnowOtherDetails {

        TransactionMode transMode = transactionRepo.findByTransactionReferenceAndDate(request.getTransactionReference(), request.getDate());
        if (transMode.getTransactionReference() == null && transMode.getDate() == null) {
            throw new UnKnowOtherDetails("you need a transactionReference to", HttpStatus.NOT_FOUND);
        }
        ReQueryResponse response = new ReQueryResponse();
        response.setDate(transMode.getDate());
        response.setTransactionMessage(transMode.getTransactionMessage());
        response.setAmount(transMode.getAmount());
        response.setTime(transMode.getTime());
        response.setCustomerName(transMode.getCustomerName());
        response.setResponseCode(transMode.getTransactionStatusCode());
        response.setTransactionReference(transMode.getTransactionReference());
        response.setResponseDesc("successFul transaction");
        response.setHashing(calculateHashes.calculateHashing(response.getTransactionReference()
                + response.getAmount()
                + response.getResponseDesc()
                + response.getCustomerName()
                + response.getTransactionMessage()
                + response.getResponseCode()
                + response.getDate()
                + response.getTime()
                + response.getTransactionStatus() + hashKey));
        return response;

    }

    @Override
    public TransactionReQueryAfterPaymentResponse reQuery(TransactionReQueryAfterPaymentRequest request) throws UnKnowTransactionReference {

        Optional<TransactionMode> mode = transactionRepo.findByReference(request.getReferenceId());

        if (mode == null && mode.isEmpty()) {
            throw new UnKnowTransactionReference("unKnow transactionReference", HttpStatus.NOT_FOUND);


        }
        TransactionMode transactionMode = new TransactionMode();
        TransactionReQueryAfterPaymentResponse response = new TransactionReQueryAfterPaymentResponse();
        response.setResponseCode(transactionMode.getTransactionStatusCode());
        response.setResponseDesc(transactionMode.getResponseDesc());
        response.setMerchantName(transactionMode.getCustomerName());
        response.setAmount(transactionMode.getAmount());
        response.setTransactionMessage(transactionMode.getTransactionMessage());
        response.setMerchantName("OZIGGYCashMoney");
        response.setHash(calculateHashes.calculateHashing(response.getResponseCode()
                + response.getCustomerName() +
                response.getTransactionStatus() +
                response.getAmount() +
                response.getTransReference()));


        return response;
    }

    @Override
    public TransactionReQueryResponse transactionReQuery(String transactionReference) throws UnKnowReferenceNumber {

        Optional<TransactionMode> mode = transactionRepo.findByTransactionReference(transactionReference);

        if (mode.isEmpty()) {
            throw new UnKnowReferenceNumber("unKnow transactionReference", HttpStatus.NOT_FOUND);

        }

        TransactionMode transactionMode = mode.get();
        TransactionReQueryResponse response = new TransactionReQueryResponse();
        response.setResponseConde(transactionMode.getTransactionStatusCode());
        response.setResponseDesc(transactionMode.getDescription());
        response.setReferenceId(transactionMode.getPaypalTransactionId());
        response.setHash(calculateHashes.calculateHashing(transactionMode.getPaypalTransactionId() + response + hashKey));

        return response;


    }

    public ValidationResponse validate(VerificationRequest request) throws UnKnowOtherDetails, InvalidCurrency {
        String referenceId = request.getReferenceId();
        BigDecimal amount = request.getTotalAmount();
        String password = request.getPassword();
        String phoneNumber = request.getPhoneNumber();

        String hash = request.getHash();


        Customer customer = repo.findByPhoneNumber(
                request.getPhoneNumber());

        if (customer == null || customer.getPhoneNumber().isEmpty()) {
            throw new UnKnowOtherDetails("unKnow phone number", HttpStatus.NOT_FOUND);
        }


        customer = repo.findByPassword(request.getPassword());
        if (customer == null || customer.getPassword().isEmpty()) {
            throw new UnKnowOtherDetails("password does not exist", HttpStatus.NOT_FOUND);
        }
        if (referenceId == null || referenceId.isEmpty()) {
            throw new UnKnowOtherDetails("reference cannot be empty", HttpStatus.NOT_FOUND);
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new UnKnowOtherDetails("amount is empty", HttpStatus.NOT_FOUND);
        }
        if (!isValidCurrency(request.getCurrency())) {
            throw new UnKnowOtherDetails("invalid currency code", HttpStatus.NOT_FOUND);

        }
        if (IsValidHash(referenceId, password, phoneNumber, hash)) {


        } else {
            throw new UnKnowOtherDetails("invalid hashing", HttpStatus.BAD_REQUEST);
        }


        return new ValidationResponse();

    }

    private boolean isValidCurrency(String currency) throws InvalidCurrency {
        Set<String> VALID_CURRENCIES=new HashSet<>(Arrays.asList("USD","EUR","GBP","JPY","CAD","AUD","CHF","CNY"));
       if (!VALID_CURRENCIES.isEmpty()){
             return VALID_CURRENCIES.contains(currency.toUpperCase());
       }
       throw new InvalidCurrency("invalid currency",HttpStatus.NOT_FOUND);

    }


    private boolean IsValidHash(String referenceId, String password, String phoneNumber, String hash) {
        return true;
    }


    @Override
    public PaymentUpdateResponse paymentUpdate(PaymentUpDateRequest request)  {

        Optional<TransactionMode> transaction = transactionRepo.findByTransactionReference(request.getTransactionReference());
        if (transaction != null && transaction.isPresent()) {

            PaymentUpdateResponse paymentUpdateResponse = new PaymentUpdateResponse();
            TransactionMode transactionMode = new TransactionMode();
            paymentUpdateResponse.setPaymentReference(transactionMode.getPaymentReference());
            paymentUpdateResponse.setResponseDescription(transactionMode.getDescription());
            paymentUpdateResponse.setTotalAmount(transactionMode.getAmount());
            paymentUpdateResponse.setPaymentReference(transactionMode.getPaymentReference());
            paymentUpdateResponse.setResponseCode(transactionMode.getResponseCode());
            paymentUpdateResponse.setHashing(calculateHashes.calculateHashing(String.valueOf(paymentUpdateResponse.getTotalAmount()
                    + paymentUpdateResponse.getPaymentReference()
                    + paymentUpdateResponse.getTransactionReference())));

            return paymentUpdateResponse;


        } else {
            PaymentUpdateResponse response = new PaymentUpdateResponse();
            TransactionMode transactionMode = new TransactionMode();
            response.setResponseDescription(transactionMode.getDescription());
            response.setResponseCode("failTransaction");


           return response;

        }






    }

}




