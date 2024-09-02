package com.example.gtpay.service.serviceImp;

import com.example.gtpay.dto.request.response.WebHookResponse;
import com.example.gtpay.model.TransactionMode;
import com.example.gtpay.repository.TransactionReferenceRepo;
import com.example.gtpay.service.PaypalWebHookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Event;
import com.paypal.api.payments.Sale;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PaypalWebhookServiceImpl implements PaypalWebHookService {


    private TransactionReferenceRepo transactionRepo;
    private ObjectMapper objectMapper;
    private CalculateHashing hashing;

    @Override
    public WebHookResponse processEvent(Event event) {
        String eventType=event.getEventType();
        WebHookResponse response=new WebHookResponse();
        switch (eventType){
            case"Payment.Sale.Complete":
                HandleSaleCompleted(event,response);
                break;
            case "Payment.Sale.Denied":
                HandleSaleDenied(event,response);
                break;
            case "Payment.Sale.Refund":
                HandleSaleRefund(event,response);
                break;
            default:
        }
        return response;
    }

    private void HandleSaleRefund(Event event, WebHookResponse response) {

        TransactionMode transactionMode=new TransactionMode();

        Sale sale= objectMapper.convertValue(event.getResource(),Sale.class);
        String saleId=sale.getId();
        String parentPayment=sale.getParentPayment();

        Optional<TransactionMode> transactionMode1=transactionRepo.findByReference(saleId);
        if (transactionMode1!=null){



            transactionMode.setTransactionStatusCode("Refund");
            transactionRepo.save(transactionMode);


        }
        response.setPaymentReference(transactionMode.getPaymentReference());
        response.setResponseDesc("payment Refunded");
        response.setStatusCode("01");
        response.setAmount(transactionMode.getAmount());
        response.setHashing(hashing.calculateHashing(response.getReference()+response.getPaymentReference()+response.getAmount()));

    }

    private void HandleSaleDenied(Event event, WebHookResponse response) {

        TransactionMode mode=new TransactionMode();
        Sale sale=objectMapper.convertValue(event.getResource(), Sale.class);
        String saleId= sale.getId();
        String parentPayment=sale.getParentPayment();

       Optional <TransactionMode> mode1=transactionRepo.findByReference(saleId);
        if (mode1==null){
            mode=new TransactionMode();
            mode.setPaymentReference(parentPayment);
            mode.setPaymentReference(saleId);
            mode.setTransactionStatusCode("Denied");
            transactionRepo.save(mode);
        }
        response.setPaymentReference(mode.getPaymentReference());
        response.setResponseDesc("payment Denied");
        response.setStatusCode("01");
        response.setAmount(mode.getAmount());
        response.setHashing(hashing.calculateHashing(response.getReference()+response.getPaymentReference()+response.getAmount()));

    }

    private void HandleSaleCompleted(Event event, WebHookResponse response) {
        TransactionMode  transactionMode=new TransactionMode();

        Sale sale= objectMapper.convertValue(event.getResource(),Sale.class);
        String saleId=sale.getId();
        String parentPayment=sale.getParentPayment();

       Optional <TransactionMode> transactionMode1=transactionRepo.findByReference(saleId);
        if (transactionMode1==null){

            transactionMode.setPaymentReference(parentPayment);
            transactionMode.setPaymentReference(saleId);
            transactionMode.setTransactionStatusCode("Completed");
            transactionRepo.save(transactionMode);


        }
        response.setPaymentReference(transactionMode.getPaymentReference());
        response.setResponseDesc("payment successful");
        response.setStatusCode("01");
        response.setAmount(transactionMode.getAmount());
        response.setHashing(hashing.calculateHashing(response.getReference()+response.getPaymentReference()+response.getAmount()));

    }


    }

