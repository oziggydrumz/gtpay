package com.example.gtpay.mapper.otpGen;

import org.springframework.stereotype.Component;

@Component

public class OtpGenerator {

    private int otpLength=4;

    public String otpGenerate(){
        int[]otpGenerator=new int[otpLength];
        for (int count=0;count<otpGenerator.length;count++){
            otpGenerator[count]=(int)(Math.random()*9);
        }
        String myOtp=""+otpGenerator[0]+otpGenerator[1]+otpGenerator[2]+otpGenerator[3];
        return myOtp;
    }
}
