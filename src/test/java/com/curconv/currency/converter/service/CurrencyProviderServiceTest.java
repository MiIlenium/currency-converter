package com.curconv.currency.converter.service;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.curconv.currency.converter.dto.wrapperDTO.CurrencyResponseDTO;
import com.curconv.currency.converter.exceptions.WebException;
import com.curconv.currency.converter.service.rest.RestApiCalls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyProviderServiceTest {

    @Mock
    RestApiCalls restApiCalls;

    @InjectMocks
    private CurrencyProviderService currencyProviderService;
    CurrencyResponseDTO currencyResponseDTO;
    @BeforeEach
    void setUp(){
        currencyResponseDTO = new CurrencyResponseDTO();
        Map<String, CurrencyDTO> data = new HashMap<>();

        CurrencyDTO usd = new CurrencyDTO();
        usd.setSymbol("$");
        usd.setName("United States Dollar");
        usd.setSymbolNative("$");
        usd.setDecimalDigits("2");
        usd.setRounding("0");
        usd.setCode("USD");
        usd.setNamePlural("US dollars");
        usd.setType("fiat");
        usd.setCountries(List.of("US"));
        usd.setValue(1.0);

        CurrencyDTO eur = new CurrencyDTO();
        eur.setSymbol("€");
        eur.setName("Euro");
        eur.setSymbolNative("€");
        eur.setDecimalDigits("2");
        eur.setRounding("0");
        eur.setCode("EUR");
        eur.setNamePlural("Euros");
        eur.setType("fiat");
        eur.setCountries(List.of("FR", "DE"));
        eur.setValue(0.85);

        data.put("USD", usd);
        data.put("EUR", eur);
        currencyResponseDTO.setData(data);
    }

    @Test
    void changeCurrency() {
        when(restApiCalls.getLatestBaseCurrency("USD")).thenReturn(currencyResponseDTO);

        CurrencyDTO result = currencyProviderService.changeCurrency("USD", "EUR", 2.0);
        assertAll(
                () -> assertNotNull(result),
                //2 euros to usd with exchange rate of 0.85 should be equal to 1.7
                () -> assertEquals(1.7, result.getValue()),
                //check that it is not equal to a lower value then expected
                () -> assertNotEquals(1.2, result.getValue())
        );
    }

    @Test
    void amountEqualZero(){
        assertThrows(WebException.class, () -> currencyProviderService.changeCurrency("USD", "EUR", 0.0));
    }

    @Test
    void amountLessThanZero(){
        assertThrows(WebException.class, () -> currencyProviderService.changeCurrency("USD", "EUR", -2.0));
    }
}