package com.example.gtpay.dto.request.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paypal.api.payments.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentUpdateResponse {

    @JsonProperty("reference_id")
    private String referenceId;

    @JsonProperty("transactionReference")
    private String transactionReference;

    @JsonProperty("paymentReference")
    private String paymentReference;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("response_description")
    private String responseDescription;

    @JsonProperty("hashing")
    private  String hashing;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;






}
