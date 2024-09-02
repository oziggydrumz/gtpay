package com.example.gtpay.dto.request;

import lombok.Data;

@Data

public class CustomerRegisterRequest {
    private String firstName;
    private String lastName;
    private String  email;
    private String passWord;
    private String phoneNumber;


}
