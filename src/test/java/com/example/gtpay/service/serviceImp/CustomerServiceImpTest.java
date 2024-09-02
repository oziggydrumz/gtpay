package com.example.gtpay.service.serviceImp;

import com.example.gtpay.dto.request.CustomerRegisterRequest;
import com.example.gtpay.dto.request.OtpValidationRequest;
import com.example.gtpay.dto.request.response.AuthenticationResponse;
import com.example.gtpay.mapper.CustomerMapper;
import com.example.gtpay.mapper.otpGen.OtpGenerator;
import com.example.gtpay.model.Customer;
import com.example.gtpay.model.OtpValidation;
import com.example.gtpay.repository.CustomerRepo;
import com.example.gtpay.repository.OtpValidationRepo;
import com.example.gtpay.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {


        @Mock
        private CustomerRepo customerRepo;

        @Mock
        private OtpValidationRequest request1;



        @Mock
        private OtpGenerator otpGenerator;

        @Mock
        private CustomerMapper mapper;

     //   @Mock
     //   private AuthenticationResponse response;

        @InjectMocks
        private CustomerServiceImp customerServiceImp;

        @Mock
        private CustomerRegisterRequest request;
        @Mock
        private Customer customer;

        @Mock
        private AuthenticationResponse response;

        @Mock
        private OtpValidation otpValidation;

        @Mock
        private OtpValidationRepo repo;

    @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);

            // Initialize your CustomerRegisterRequest and Customer objects
            request = new CustomerRegisterRequest();
            request.setEmail("test@example.com");
            request.setFirstName("John");
            request.setLastName("Doe");
            request.setPassWord("password123");
            request.setPhoneNumber("1234567890");

            customer = new Customer();
            customer.setEmail(request.getEmail());
            customer.setFirstName(request.getFirstName());
            customer.setLastname(request.getLastName());
            customer.setPassword(request.getPassWord());
            customer.setPhoneNumber(request.getPhoneNumber());

            otpValidation=new OtpValidation();
        otpValidation.setToken(response.getToken());
        otpValidation.setId(0);
        otpValidation.setCustomer(customer);

          //  customer = null; // To simulate customer does not already exist
        }

        @Test
        public void testCustomerRegistration_Success() throws CustomerAlReadyExist {
            when(customerRepo.findByEmail(request.getEmail())).thenReturn(null);

            when(otpGenerator.otpGenerate()).thenReturn("98101");



            response= (customerServiceImp.customerRegistration(request));


            System.out.println("customer is:"+customer);
            System.out.println("my otpGenerator:"+otpGenerator.otpGenerate());
            verify(customerRepo,times(1)).findByEmail(request.getEmail());
            verify(customerRepo,times(1)).save(customer);
            ArgumentCaptor<OtpValidation> captor = ArgumentCaptor.forClass(OtpValidation.class);

            verify(repo,times(1)).save(captor.capture());
            OtpValidation capturedOtpValidation = captor.getValue();

            assertEquals("98101",response.getToken());
            assertEquals("John", capturedOtpValidation.getCustomer().getFirstName());




                  }






    @Test
    void confirmation() throws OtpValidationCanNotBeNull, AccountNotConfirm {
      // OtpValidation validation=new OtpValidation();
        request1.setToken("981081");
       otpValidation.setToken(request1.getToken());
       otpValidation.setExpire(LocalDateTime.now().plusMinutes(2));
       otpValidation.setConfirm(LocalDateTime.now());

       customer.setEnable(false);
       otpValidation.setCustomer(customer);

      lenient(). when(repo.findByToken(request1.getToken())).thenReturn(otpValidation);
    lenient(). when(otpValidation.getExpire()).thenReturn(LocalDateTime.now().plusMinutes(2));

       String result = customerServiceImp.confirmation(request1);

       verify(repo,times(1)).save(otpValidation);
       verify(repo,times(1)).findByToken(request1.getToken());
       assertTrue(customer.isEnable());
       assertNotNull(otpValidation.getConfirm());
       assertEquals("Account Confirmed",result);
       assertNotNull(otpValidation.getExpire());




    }

    @Test
    void login() {
    }

    @Test
    void findCustomer() {
    }
}