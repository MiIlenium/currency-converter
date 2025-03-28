package com.curconv.currency.converter.service.rest;

import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "currencyapiservice", url = "https://api.currencyapi.com", configuration = FeignConfig.class)
@Validated
public interface RestApiCalls {

    @GetMapping("/v3/currencies")
    CurrencyResponseDTO getCurrencies();

    @GetMapping("/v3/latest")
    CurrencyResponseDTO getLatestCurrency();

    @GetMapping("/v3/latest")
    CurrencyResponseDTO getLatestBaseCurrency(@RequestParam("base_currency") String baseCurrency);
}
