package com.curconv.currency.converter.service.rest;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Value("${api.key}")
    private String apiKey;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("apikey", apiKey); // Use 'apikey' instead of 'Authorization'
    }
}
