package com.example.gtpay.config;

import com.example.gtpay.mapper.KeyConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component

public class JwtService {
    private final KeyConfig keyConfig;

    public JwtService(KeyConfig keyConfig){
        this.keyConfig=keyConfig;
    }

    public String extractEmail(String token) {
        return extractEmail(token);
    }
    public String extractClaim(String token){

        return extractAllClaims(token);
    }

    private String extractAllClaims(String token) {
  return Jwts
          .parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    }

    public String generateToken(String email){
        return generateToken(email);

    }
    public String createToke(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(keyConfig.getSecrete().getBytes(StandardCharsets.UTF_8));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try{
            String email=extractEmail(token);
            return email.equals(userDetails.getUsername())&&!isTokenExpired(token);
        }catch (JwtException | IllegalArgumentException e){
            throw new IllegalArgumentException("invalid or expired token");
        }

    }

    private boolean isTokenExpired(String token) {
        Claims  claims=Jwts.parserBuilder()
                .setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());


    }

}
