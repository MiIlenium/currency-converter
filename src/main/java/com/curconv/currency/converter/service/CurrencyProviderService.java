package com.curconv.currency.converter.service;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import com.curconv.currency.converter.service.rest.RestApiCalls;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class CurrencyProviderService {

    private final RestApiCalls restApiCalls;

    public CurrencyDTO getCurrencyInfoByCountryCode(String countryCode) {
        log.debug("Retrieving currency info for country code {}", countryCode);
        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getCurrency();
        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO getLatestExchangeRateByCountryCode(String countryCode) {
        log.debug("Retrieving latest exchange rate for country code {}", countryCode);
        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getLatestCurrency();
        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO changeCurrency(String baseCurrency, String convertToCurrency, double amount) {
        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getLatestBaseCurrency(baseCurrency);

        //Get new object of currency and make calculations, return result
        CurrencyDTO currencyDTO = currencyResponseDTO.getData().get(convertToCurrency);
        currencyDTO.setValue(currencyResponseDTO.getData().get(convertToCurrency).getValue() * amount);

        return currencyDTO;
    }
}

