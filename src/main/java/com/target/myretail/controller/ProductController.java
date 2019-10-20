package com.target.myretail.controller;

import com.target.myretail.dto.PriceDto;
import com.target.myretail.dto.ProductDto;
import com.target.myretail.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class ProductController {

    public static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("v1/products/{id}")
    public ResponseEntity<ProductDto> getProduct (@PathVariable("id") long productId)
            throws ExecutionException, InterruptedException
    {
        LOG.info("Get product details for id: {}", productId);
        ProductDto productDto = productService.getProductInfo(productId);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("v1/products")
    public ResponseEntity<?> createProduct (@RequestBody ProductDto product, UriComponentsBuilder componentsBuilder)
    {
        LOG.info("Create product price: {}", product);
        ProductDto productDto = productService.updateProduct(product);
        UriComponents uriComponents = componentsBuilder.path("/api/v1/products/{id}").buildAndExpand(productDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("v1/products/{id}")
    public ResponseEntity<ProductDto> updateProduct (@PathVariable("id") long productId, @RequestBody ProductDto product)
    {
        LOG.info("Update product price: {}, {}", productId, product);
        ProductDto productDto = productService.updateProduct(product);
        return ResponseEntity.ok(productDto);
    }

    /**
     * MOCK Price API to be hosted on a separate service
     * as described in case study https://price.myRetail.com/product/{productId}/price
     * @param productId
     * @return
     */
    @GetMapping("v1/products/{id}/price")
    public ResponseEntity<PriceDto> getProductPrice (@PathVariable("id") long productId)
    {
        LOG.info("Get product price for id: {}", productId);
        PriceDto priceDto = productService.getProductPrice(productId);
        return ResponseEntity.ok(priceDto);
    }
}
