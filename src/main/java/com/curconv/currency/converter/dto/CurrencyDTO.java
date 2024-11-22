package com.curconv.currency.converter.dto;

import com.curconv.currency.converter.Security.AppRegex;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyDTO {
    @JsonProperty("symbol")
    String symbol;
    @JsonProperty("name")
    @Nullable
    @Pattern(regexp = AppRegex.COUNTRY_NAME_CASE_INSENSITIVE)
    String name;
    @JsonProperty("symbol_native")
    String symbolNative;
    @JsonProperty("decimal_digits")
    String decimalDigits;
    @JsonProperty("rounding")
    String rounding;
    @JsonProperty("code")
    @Pattern(regexp = AppRegex.THREE_DIGITS_COUNTRY_CODE)
    String code;
    @JsonProperty("name_plural")
    String namePlural;
    @JsonProperty("type")
    String type;
    @JsonProperty("countries")
    @Nullable
    List<@Pattern(regexp = AppRegex.TWO_DIGITS_COUNTRY_CODE) String> countries;
    @JsonProperty("value")
    double value;
}
