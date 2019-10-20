package com.target.myretail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProductDto implements Serializable {

    private long   id;
    private String name;
    @JsonProperty("current_price")
    private PriceDto  currentPrice;

    public ProductDto(){}

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public PriceDto getCurrentPrice ()
    {
        return currentPrice;
    }

    public void setCurrentPrice (PriceDto currentPrice)
    {
        this.currentPrice = currentPrice;
    }

}
