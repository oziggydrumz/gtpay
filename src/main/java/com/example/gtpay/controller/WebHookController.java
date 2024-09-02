package com.example.gtpay.controller;

import com.example.gtpay.dto.request.response.WebHookResponse;
import com.example.gtpay.service.PaypalWebHookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Event;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.HttpMethod;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/webHook")
public class WebHookController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PaypalWebHookService service;






    @PostMapping("/paymentUpdateHandledByWebHook")
    public ResponseEntity<WebHookResponse>handleWebHook(@RequestBody String payLoad,
                                                        @RequestHeader("paypal-transmission-sig")String transmissionSig,
                                                        @RequestHeader("Paypal-Transmission-time")String transmissionTime,
                                                        @RequestHeader("paypal-Transmission-Id")String transmissionId,
                                                        @RequestHeader("paypal-Transmission-Cert-Url")String certUrl,
                                                        @RequestHeader("Paypal-Transmission-Auth-Algo")String authAlgo){
        try{


            boolean isValid=validateWebHookEvent(transmissionId,transmissionSig,transmissionTime,certUrl,authAlgo,payLoad);
            if (isValid){

                Event event=mapper.readValue(payLoad,Event.class);
                WebHookResponse response=service.processEvent(event);
                return ResponseEntity.ok(response);

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

    }

    private boolean validateWebHookEvent(String transmissionId, String transmissionSig, String transmissionTime, String certUrl, String authAlgo, String payLoad) throws JsonProcessingException {

        Map<String, String>header=new HashMap<>();
        header.put("Paypal-Transmission-sig",transmissionSig);
        header.put("Paypal-Transmission-Time",transmissionTime);
        header.put("Paypal-Transmission-id",transmissionId);
        header.put("Paypal-Transmission-Cert-Url",certUrl);
        header.put("Paypal-Transmission-Auth-Algo",authAlgo);

        String webhookId="";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("webhook_id", webhookId);
        requestBody.put("transmission_id", transmissionId);
        requestBody.put("transmission_time", transmissionTime);
        requestBody.put("cert_url", certUrl);
        requestBody.put("auth_algo", authAlgo);
        requestBody.put("transmission_sig", transmissionSig);
        requestBody.put("webhook_event", mapper.readValue(payLoad, Map.class));





    try {
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create("\"https://api.sandbox.paypal.com/v1/notifications/verify-webhook-signature\""))
                .header("content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);
            return "SUCCESS".equals(responseBody.get("verification_status"));
        } else {
            System.err.println("Failed to verify webhook signature. Status code: " + response.statusCode());
            return false;
        }
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return false;


    }
}

    }





