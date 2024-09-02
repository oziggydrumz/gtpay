package com.example.gtpay.controller;

import com.example.gtpay.dto.request.CustomerRegisterRequest;
import com.example.gtpay.service.CustomerService;
import com.example.gtpay.service.serviceImp.CustomerAlReadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/customerRegistration")
    public ResponseEntity<?>customerRegistration(@RequestBody CustomerRegisterRequest request) throws CustomerAlReadyExist {
        return new ResponseEntity<>(customerService.customerRegistration(request), HttpStatus.OK);

    }

    @GetMapping("/hello")

    public String getMessage(String message){
        System.out.println(message);
        return "hello";
    }
}
