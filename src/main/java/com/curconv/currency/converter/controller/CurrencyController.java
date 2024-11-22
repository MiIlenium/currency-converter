package com.curconv.currency.converter.controller;

import com.curconv.currency.converter.Security.AppRegex;
import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.service.CurrencyProviderService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class CurrencyController {
    private final CurrencyProviderService currencyProviderService;

    @GetMapping("/api/currency/{code}")
    public CurrencyDTO getInfoByCode(@PathVariable @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE) String code) {
        return currencyProviderService.getCurrencyInfoByCountryCode(code);
    }

    @GetMapping("/api/currency/exchange/{code}")
    public CurrencyDTO getExchangeInfoByCode(@PathVariable @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE) String code) {
        return currencyProviderService.getLatestExchangeRateByCountryCode(code);
    }

    @GetMapping("/api/currency/exchange")
    public CurrencyDTO getExchangeInfoByBaseCurrency(@RequestParam String baseCurrency,
                                                     @RequestParam String convertToCurrency,
                                                     @RequestParam double amountToConvert) {
        return currencyProviderService.changeCurrency(baseCurrency, convertToCurrency, amountToConvert);
    }
}
