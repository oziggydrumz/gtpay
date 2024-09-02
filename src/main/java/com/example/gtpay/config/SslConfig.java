package com.example.gtpay.config;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SslConfig {




        @Bean
        public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sslCustomizer() {
            return factory -> {
                Ssl ssl = new Ssl();
                ssl.setKeyStore("classpath:keystore.p12");
                ssl.setKeyStorePassword("Osazee98108@");
                ssl.setKeyAlias("selfsigned");
                ssl.setKeyStoreType("PKCS12");
                factory.setSsl(ssl);
                factory.setPort(443);
            };
        }
    }


