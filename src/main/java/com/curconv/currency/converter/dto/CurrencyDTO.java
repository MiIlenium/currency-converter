package com.curconv.currency.converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyDTO {
    @JsonProperty("symbol")
    String symbol;
    @JsonProperty("name")
    String name;
    @JsonProperty("symbol_native")
    String symbolNative;
    @JsonProperty("decimal_digits")
    long decimalDigits;
    @JsonProperty("rounding")
    long rounding;
    @JsonProperty("code")
    String code;
    @JsonProperty("name_plural")
    String namePlural;
    @JsonProperty("value")
    long value;
}
