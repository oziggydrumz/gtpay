package com.example.gtpay.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data

public class CustomerDetails implements UserDetails {
    private long id;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority>authorities;



    public CustomerDetails(long id, String password, List<GrantedAuthority> authorities, String email) {
        this.id=id;
        this.password=password;
        this.authorities=authorities;
        this.email=email;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return email;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return false;
    }
}
