package com.example.gtpay.mapper;

import com.example.gtpay.dto.request.CustomerRegisterRequest;
import com.example.gtpay.model.Customer;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component

public class CustomerMapper {
  //  @Autowired
  //  private PasswordEncoder passwordEncoder;

    public Customer customerMapper(CustomerRegisterRequest request){
        Customer customer=new Customer();
        customer.setEmail(request.getEmail());
        customer.setLastname(request.getLastName());
        customer.setFirstName(request.getFirstName());
        customer.setPassword(request.getPassWord());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customer;

    }



}
