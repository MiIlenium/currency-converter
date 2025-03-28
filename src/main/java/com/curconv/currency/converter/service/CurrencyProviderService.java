package com.curconv.currency.converter.service;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import com.curconv.currency.converter.exceptions.WebException;
import com.curconv.currency.converter.security.AppRegex;
import com.curconv.currency.converter.service.rest.RestApiCalls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Service for providing currency data and performing currency-related operations, such as retrieving currency
 * information, fetching exchange rates, and currency conversions.
 *
 * This service interacts with external APIs via the {@code RestApiCalls} interface to retrieve currency-related
 * information. It performs various validations and computations to provide desired outcomes.
 *
 * The main features of this service include:
 * - Fetching currency details by country code.
 * - Retrieving the latest exchange rate for a specific currency.
 * - Performing currency conversion between different currencies based on specified amounts and exchange rates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class CurrencyProviderService {

    private final RestApiCalls restApiCalls;

    public CurrencyDTO getCurrencyInfoByCountryCode(@PathVariable
                                                    @Valid
                                                    @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE,
                                                            message = "Country code must be exactly 3 uppercase letters (e.g., 'USD', 'EUR').") String countryCode) {
        log.debug("Retrieving currency info for country code {}", countryCode);
        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getCurrencies();
        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO getLatestExchangeRateByCountryCode(String countryCode) {
        log.debug("Retrieving latest exchange rate for country code {}", countryCode);
        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getLatestCurrency();
        return currencyResponseDTO.getData().get(countryCode);
    }

    public CurrencyDTO changeCurrency(String baseCurrency, String convertToCurrency, double amount) {
        if (amount <= 0) {
            throw new WebException("Amount must be greater than zero", HttpStatus.BAD_REQUEST);
        }

        CurrencyResponseDTO currencyResponseDTO = restApiCalls.getLatestBaseCurrency(baseCurrency);

        //Get new object of currency and make calculations, return result
        CurrencyDTO currencyDTO = currencyResponseDTO.getData().get(convertToCurrency);
        currencyDTO.setValue(currencyResponseDTO.getData().get(convertToCurrency).getValue() * amount);

        return currencyDTO;
    }
}

