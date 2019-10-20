package com.target.myretail.entity;

import com.target.myretail.dto.PriceDto;
import com.target.myretail.dto.ProductDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Price {

    @Id
    private Long productId;
    private BigDecimal value;
    private String currencyCode;

    public Price(){}

    public Price(ProductDto productDto){
        PriceDto currentPrice = productDto.getCurrentPrice();
        this.productId = productDto.getId();
        this.value = currentPrice.getValue();
        this.currencyCode = currentPrice.getCurrencyCode();
    }

    public Long getProductId ()
    {
        return productId;
    }

    public void setProductId (Long productId)
    {
        this.productId = productId;
    }

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
}
