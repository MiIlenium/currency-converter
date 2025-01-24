package com.curconv.currency.converter.service;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import com.curconv.currency.converter.exceptions.WebException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CurrencyProviderService {
    @Value("${api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ALL_CURRENCIES_ENDPOINT = "/v3/currencies";
    private static final String GET_LATEST_ALL_CURRENCIES_ENDPOINT = "/v3/latest";
    private static final String LATEST_BASE_CURRENCY = "/v3/latest?base_currency=";
    private static final String ERROR_OCCURRED_WHILE_RETRIEVING_THE_DATA = "Error occurred while retrieving the data";
    private static final String COULD_NOT_GET_DATA = "Could not get data from service: {}";

    public CurrencyDTO getCurrencyInfoByCountryCode(String countryCode) {
        CurrencyResponseDTO currencyResponseDTO;
        log.debug("Retrieving currency info for country code {}", countryCode);
        try {
            currencyResponseDTO = objectMapper.readValue(sendRequest(ALL_CURRENCIES_ENDPOINT, HttpMethod.GET), CurrencyResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error(COULD_NOT_GET_DATA, e);
            throw new WebException(ERROR_OCCURRED_WHILE_RETRIEVING_THE_DATA, HttpStatus.BAD_REQUEST);
        }

        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO getLatestExchangeRateByCountryCode(String countryCode) {
        CurrencyResponseDTO currencyResponseDTO;
        log.debug("Retrieving latest exchange rate for country code {}", countryCode);
        try {
            currencyResponseDTO = objectMapper.readValue(sendRequest(GET_LATEST_ALL_CURRENCIES_ENDPOINT, HttpMethod.GET), CurrencyResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error(COULD_NOT_GET_DATA, e);
            throw new WebException(ERROR_OCCURRED_WHILE_RETRIEVING_THE_DATA, HttpStatus.BAD_REQUEST);
        }

        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO changeCurrency(String baseCurrency, String convertToCurrency, double amount) {
        CurrencyResponseDTO currencyResponseDTO;

        try {
            currencyResponseDTO = objectMapper.readValue(sendRequest(LATEST_BASE_CURRENCY.concat(baseCurrency), HttpMethod.GET), CurrencyResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error(COULD_NOT_GET_DATA, e);
            throw new WebException(ERROR_OCCURRED_WHILE_RETRIEVING_THE_DATA, HttpStatus.BAD_REQUEST);
        }
        //Get new object of currency and make calculations, return result
        CurrencyDTO currencyDTO = currencyResponseDTO.getData().get(convertToCurrency);
        currencyDTO.setValue(currencyResponseDTO.getData().get(convertToCurrency).getValue() * amount);

        return currencyDTO;
    }

    private String sendRequest(String apiAddress, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUri = "https://api.currencyapi.com".concat(apiAddress);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("apikey", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Configure ObjectMapper to ignore unknown properties
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return restTemplate.exchange(baseUri, httpMethod, entity, String.class).getBody();
        } catch (Exception e) {
            log.error("Error during API call", e);
            throw new WebException("Error making API request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

