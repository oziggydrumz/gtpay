package com.example.gtpay.model.security;

import com.example.gtpay.model.Customer;
import com.example.gtpay.repository.CustomerRepo;
import com.example.gtpay.service.serviceImp.CustomerDoesNotExist;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepo repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer=repo.findByEmail(email);
        if (customer==null){
            try {

                throw new CustomerDoesNotExist("customer not found", HttpStatus.NOT_FOUND);
            } catch (CustomerDoesNotExist e) {
                throw new RuntimeException(e);
            }
        }
        List<GrantedAuthority>authorities= Collections.singletonList(
                new SimpleGrantedAuthority("Role_customer")
        );

        return new CustomerDetails(customer.getId(),
                customer.getPassword(),
               authorities,
               customer.getEmail() );

    }
}
