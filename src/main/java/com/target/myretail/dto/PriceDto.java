package com.target.myretail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.target.myretail.entity.Price;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceDto implements Serializable {

    private BigDecimal value;

    @JsonProperty("currency_code")
    private String currencyCode;

    public PriceDto(){}

    public BigDecimal getValue ()
    {
        return value;
    }

    public void setValue (BigDecimal value)
    {
        this.value = value;
    }

    public String getCurrencyCode ()
    {
        return currencyCode;
    }

    public void setCurrencyCode (String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public static PriceDto toDTO (Price value)
    {
        PriceDto dto = new PriceDto();
        dto.setValue(value.getValue());
        dto.setCurrencyCode(value.getCurrencyCode());
        return dto;
    }
}
