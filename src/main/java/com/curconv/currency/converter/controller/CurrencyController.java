package com.curconv.currency.converter.controller;

import com.curconv.currency.converter.security.AppRegex;
import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.service.CurrencyProviderService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class CurrencyController {
    private final CurrencyProviderService currencyProviderService;
    @GetMapping("/api/currency/{code}")
    public CurrencyDTO getInfoByCode(@PathVariable @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE) String code) {
        log.info("Getting currency info for code {}", code);
        return currencyProviderService.getCurrencyInfoByCountryCode(code);
    }

    @GetMapping("/api/currency/exchange/{code}")
    public CurrencyDTO getExchangeInfoByCode(@PathVariable @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE) String code) {
        log.info("Getting currency exchange rates for code {}", code);
        return currencyProviderService.getLatestExchangeRateByCountryCode(code);
    }

    @GetMapping("/api/currency/exchange")
    public CurrencyDTO getExchangeInfoByBaseCurrency(@RequestParam String baseCurrency,
                                                     @RequestParam String convertToCurrency,
                                                     @RequestParam double amountToConvert) {
        log.info("Exchanging from {} to {}", baseCurrency, convertToCurrency);
        return currencyProviderService.changeCurrency(baseCurrency, convertToCurrency, amountToConvert);
    }
}
