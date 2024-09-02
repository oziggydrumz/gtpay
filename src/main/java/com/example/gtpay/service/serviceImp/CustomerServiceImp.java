package com.example.gtpay.service.serviceImp;

import com.example.gtpay.config.JwtService;
import com.example.gtpay.dto.request.CustomerRegisterRequest;
import com.example.gtpay.dto.request.AuthenticationRequest;
import com.example.gtpay.dto.request.OtpValidationRequest;
import com.example.gtpay.dto.request.response.AuthenticationResponse;
import com.example.gtpay.mapper.CustomerMapper;
import com.example.gtpay.mapper.otpGen.OtpGenerator;
import com.example.gtpay.model.Customer;
import com.example.gtpay.model.OtpValidation;
import com.example.gtpay.repository.CustomerRepo;
import com.example.gtpay.repository.OtpValidationRepo;
import com.example.gtpay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service



public class CustomerServiceImp implements CustomerService {


    @Autowired
    private CustomerRepo customerRepo;

    private int otpLength=4;
@Autowired
    private OtpValidationRepo validationRepo;



   // private final CustomerMapper mapper;

   // @Autowired
   // private final OtpGenerator otpGenerator;

    private final AuthenticationManager manager;
    private JwtService jwtService;
    private OtpGenerator otpGenerator;

    public CustomerServiceImp(OtpValidationRepo validationRepo, CustomerMapper mapper, OtpGenerator otpGenerator, AuthenticationManager manager, JwtService jwtService) {
        this.validationRepo = validationRepo;
       // this.mapper = mapper;
       // this.otpGenerator = otpGenerator;
        this.manager = manager;
        this.jwtService = jwtService;
    }


    @Override
    public AuthenticationResponse customerRegistration(CustomerRegisterRequest request) throws CustomerAlReadyExist {
        Customer customer=customerRepo.findByEmail(request.getEmail());
        if(customer!=null){
            throw new CustomerAlReadyExist("customer already exist", HttpStatus.ALREADY_REPORTED);
        }

     CustomerMapper mapper=new CustomerMapper();
        Customer customer1=mapper.customerMapper(request);


        System.out.println("Customer before save: " + customer1);

        String otpGen=otpGenerator.otpGenerate();

       // OtpGenerator otpGen=new OtpGenerator();


        OtpValidation otpValidation=new OtpValidation();
        otpValidation.setCustomer(customer1);
        otpValidation.setToken((otpGen));
        otpValidation.setConfirm(LocalDateTime.now());
        otpValidation.setExpire(LocalDateTime.now().plusMinutes(60));



        validationRepo.save(otpValidation);
        customerRepo.save(customer1);


        return AuthenticationResponse.builder()
                .token(otpGen)
                .build();
    }

    @Override
    public String confirmation(OtpValidationRequest request) throws OtpValidationCanNotBeNull, AccountNotConfirm {
        OtpValidation validation=validationRepo.findByToken(request.getToken());
        if (validation==null){
            throw new OtpValidationCanNotBeNull("unKnow token",HttpStatus.NOT_FOUND);

        }
        Customer customer=validation.getCustomer();
        LocalDateTime expireAt=validation.getExpire();
        LocalDateTime confirmAt=validation.getConfirm();

        if (confirmAt==null){
            throw new AccountNotConfirm("your Account Has Not Been Confirm",HttpStatus.BAD_REQUEST);
        }
        boolean isExpired=expireAt.isBefore(LocalDateTime.now());
        if (isExpired){
            validation.setCustomer(null);
            validationRepo.delete(validation);
            customerRepo.delete(customer);
            return "expired token";

        }
        customer.setEnable(true);
        validationRepo.save(validation);
        validation.setConfirm(LocalDateTime.now());
        customerRepo.save(customer);

        return "Account Confirmed";







    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

     manager.authenticate(new UsernamePasswordAuthenticationToken(
             request.getEmail(),
             request.getPassword()
     ));


     var jwtToken=jwtService.generateToken(request.getEmail());
     return AuthenticationResponse.builder()
             .token(jwtToken)
             .build();

    }

    @Override
    public Customer findCustomer(String email) throws CustomerDoesNotExist {
        Customer customer=customerRepo.findByEmail(email);
        if (customer==null){
            throw new CustomerDoesNotExist("customer doesNot exist",HttpStatus.NOT_FOUND);
        }
        customerRepo.findByEmail(customer.getEmail());

        return customer;

    }
}
