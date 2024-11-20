package com.curconv.currency.converter.service;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyProviderService {
    @Value("${api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String allCurrenciesEndpoint = "/v3/currencies";
    private static final String getLatestCurrenciesEndpoint = "/v3/latest";
    public CurrencyDTO getCurrencyInfoByCountryCode(String countryCode) {
        CurrencyResponseDTO currencyResponseDTO;
        log.debug("Retrieving currency info for country code {}", countryCode);
        try {
            currencyResponseDTO = objectMapper.readValue(
                    (String) sendRequest(allCurrenciesEndpoint, HttpMethod.GET).getBody(),
                    CurrencyResponseDTO.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO getLatestExchangeRateByCountryCode(String countryCode) {
        CurrencyResponseDTO currencyResponseDTO;
        log.debug("Retrieving latest exchange rate for country code {}", countryCode);
        try {
            currencyResponseDTO = objectMapper.readValue(
                    (String) sendRequest(getLatestCurrenciesEndpoint, HttpMethod.GET).getBody(),
                    CurrencyResponseDTO.class
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return currencyResponseDTO.getData().get(countryCode);
    }

    private ResponseEntity sendRequest(String apiAddress, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUri = "https://api.currencyapi.com".concat(apiAddress);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("apikey", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Configure ObjectMapper to ignore unknown properties
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Perform the API call and map directly to a DTO
            return restTemplate.exchange(
                    baseUri,
                    httpMethod,
                    entity,
                    String.class
            );
        } catch (Exception e) {
            log.error("Error during API call", e);
            throw new RuntimeException("Error making API request", e);
        }
    }
}

