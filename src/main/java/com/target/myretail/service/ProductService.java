package com.target.myretail.service;

import com.target.myretail.dto.PriceDto;
import com.target.myretail.dto.ProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    String PRODUCT_TITLE_PATH = "/product/item/product_description/title";

    ProductDto getProductInfo (long productId) throws ExecutionException, InterruptedException;

    ProductDto updateProduct (ProductDto productDto);

    PriceDto getProductPrice(long productId);

}
