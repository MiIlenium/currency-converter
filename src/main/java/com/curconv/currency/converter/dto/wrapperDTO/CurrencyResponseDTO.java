package com.curconv.currency.converter.dto.wrapperDTO;

import com.curconv.currency.converter.dto.CurrencyDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CurrencyResponseDTO {
    @JsonProperty("data")
    private Map<String, CurrencyDTO> data;
}
