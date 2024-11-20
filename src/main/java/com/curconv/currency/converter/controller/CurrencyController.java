package com.curconv.currency.converter.controller;
import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.service.CurrencyProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyProviderService currencyProviderService;

    @GetMapping("/api/getSomething")
    public Object sendRequest(){
        return null;
    }

    @GetMapping("/api/currency/{code}")
    public CurrencyDTO getInfoByCode(@PathVariable String code){
        return currencyProviderService.getCurrencyInfoByCountryCode(code);
    }

    @GetMapping("/api/currency/exchange/{code}")
    public CurrencyDTO getExchangeInfoByCode(@PathVariable String code){
        return currencyProviderService.getLatestExchangeRateByCountryCode(code);
    }
}
