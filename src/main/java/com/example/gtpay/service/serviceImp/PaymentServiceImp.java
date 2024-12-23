package com.example.gtpay.service.serviceImp;

import com.braintreepayments.http.HttpRequest;
import com.example.gtpay.config_properties.NgrokConfigurationProperties;
import com.example.gtpay.dto.request.PaymentUpDateRequest;
import com.example.gtpay.dto.request.response.PaymentResponse;
import com.example.gtpay.model.Customer;
import com.example.gtpay.model.TransactionMode;
import com.example.gtpay.repository.CustomerRepo;
import com.example.gtpay.repository.TransactionReferenceRepo;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.management.RuntimeErrorException;
import javax.swing.text.html.Option;
//import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class PaymentServiceImp {

    private final PaypalService paypalService;

    private final NgrokConfigurationProperties configurationProperties;
    @Autowired
    private final TransactionReferenceRepo transactionRepo;
    @Autowired
    private final CustomerRepo customerRepo;


    public String createPayment(Customer customer) throws UnKnowOtherDetails, ErrorCreatingPayment {

      Customer customer1=customerRepo.findByEmail(customer.getEmail());
        if (customer1==null){
            return String.format("Redirected to customer registration %s/customer/customerRegistration", configurationProperties.getBasePath()) ;

        }

        try {
            String cancelUrl = "http://localhost:8080/paypal/paymentCancelled";
            String successUrl = "http://localhost:8080/paypal/paymentSuccess";

            String paymentReference= UUID.randomUUID().toString();




            Payment payment = paypalService.createPayment(
                    10.00,
                    "osazee",
                    "Description of the payment",
                    "paypal",
                    "USD",
                    "sale",
                    cancelUrl,
                    successUrl
            );

            String approvalUrl = payment.getLinks().stream()
                    .filter(link -> "approval_url".equals(link.getRel()))
                    .map(link -> link.getHref())
                    .findFirst()
                    .orElseThrow(() -> new UnKnowOtherDetails("Approval URL not found",HttpStatus.NOT_FOUND));

            // Save transaction reference (optional)
            Transaction transaction = new Transaction();
            transaction.setInvoiceNumber(payment.getId());
            transaction.setInvoiceNumber(paymentReference);

            TransactionMode mode = new TransactionMode();
            mode.setTransactionReference(transaction.getInvoiceNumber());
            mode.setPaymentReference(transaction.getInvoiceNumber());
            transactionRepo.save(mode);

            return approvalUrl;
        } catch (PayPalRESTException e) {
            throw new ErrorCreatingPayment("Error creating payment", e);
        }
    }
    @Transactional

    public String paymentSuccess(HttpServletRequest request) throws UnKnowOtherDetails, ErrorCreatingPayment {
        try {
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("PayerID");

            Payment payment = paypalService.paymentExecution(paymentId, payerId);

            if ("approved".equalsIgnoreCase(payment.getState())) {
                Transaction transaction = payment.getTransactions().get(0);
                Optional<TransactionMode> transMode = transactionRepo.findByTransactionReference(paymentId);

                if (transMode.isPresent()) {
                    TransactionMode mode = transMode.get();

                    // Update transaction details
                    mode.setTransactionStatusCode("001");
                    mode.setPaypalTransactionId(payment.getId());
                    mode.setCustomerName("Customer Name"); // You should replace this with actual customer info
                    mode.setTransDescription(transaction.getDescription());
                    mode.setAmount(BigDecimal.valueOf(Double.parseDouble(transaction.getAmount().getTotal())));
                    mode.setTransactionMessage("Transaction successful");
                    mode.setTime(new SimpleDateFormat("HH:mm:ss.SS").format(new Date()));
                    mode.setDate(new Date());

                    transactionRepo.save(mode);

                    return "Transaction successful";
                }
            }
        } catch (PayPalRESTException e) {
            throw new ErrorCreatingPayment("Error executing payment", e);
        }
        return "Transaction failed";
    }

    @Transactional

    public String paymentCancel(String paymentId) {
        Optional<TransactionMode> transactionMode = transactionRepo.findByTransactionReference(paymentId);

        if (transactionMode.isPresent()) {
            TransactionMode mode = transactionMode.get();
            mode.setTransactionStatusCode("cancelled");
            transactionRepo.save(mode);
            return "Payment cancelled";
        }
        return "Transaction not found";
    }
}
