package com.example.gtpay.controller;

import com.example.gtpay.model.Customer;
import com.example.gtpay.service.serviceImp.ErrorCreatingPayment;
import com.example.gtpay.service.serviceImp.PaymentServiceImp;
import com.example.gtpay.service.serviceImp.UnKnowOtherDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/paypal")
public class paypalController {
    @Autowired
    private final PaymentServiceImp paymentServiceImp;

    public paypalController(PaymentServiceImp paymentServiceImp) {
        this.paymentServiceImp = paymentServiceImp;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(Customer customer) {
        try {
            String approvalUrl = paymentServiceImp.createPayment(customer);
            return new ResponseEntity<>(approvalUrl, HttpStatus.CREATED);
        } catch (UnKnowOtherDetails | ErrorCreatingPayment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paymentSuccess")
    public ResponseEntity<?> executePayment(HttpServletRequest request) {
        try {
            String response = paymentServiceImp.paymentSuccess(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnKnowOtherDetails e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ErrorCreatingPayment e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/paymentCancelled")
    public ResponseEntity<?> paymentCancelled(@RequestParam String paymentId) {
        try {
            String response = paymentServiceImp.paymentCancel(paymentId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/osazee")
    public String osazee(){
        return osazee();
    }
}
