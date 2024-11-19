package com.curconv.currency.converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Currency {
    @JsonProperty("name")
    String name;
    @JsonProperty("symbol")
    String symbol;
    @JsonProperty("decimal_digits")
    long decimalDigits;
    @JsonProperty("rounding")
    long rounding;
    @JsonProperty("countries")
    List<String> countries;
}
