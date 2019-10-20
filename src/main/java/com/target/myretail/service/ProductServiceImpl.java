package com.target.myretail.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.myretail.dto.PriceDto;
import com.target.myretail.dto.ProductDto;
import com.target.myretail.entity.Price;
import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${my-retail.product-info.api.url}")
    private String productApiUrl;

    @Value("${my-retail.product-price.api.url}")
    private String productPriceApiUrl;

    /**
     * As product details and price to be retrieved from two different services
     * Two parallel API calls will be fired each service and aggregated response will be sent back
     *
     * @param productId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Cacheable(key = "#productId", cacheNames = { "productsCache" })
    @Override
    public ProductDto getProductInfo (long productId)
    {

        LOG.info("Calling get product info {}", productId);

        String productApiEndpoint = String.format(productApiUrl, productId);
        String priceApiEndpoint = String.format(productPriceApiUrl, productId);

        Future<JsonNode> productResponseFuture = CompletableFuture.supplyAsync(() -> restTemplate.getForObject(productApiEndpoint, JsonNode.class));
        Future<PriceDto> priceResponseFuture = CompletableFuture.supplyAsync(() -> restTemplate.getForObject(priceApiEndpoint, PriceDto.class));

        ProductDto productDto = null;
        try {
            JsonNode productResponse = productResponseFuture.get();
            PriceDto priceResponse = priceResponseFuture.get();

            String name = productResponse.at(PRODUCT_TITLE_PATH).asText();;
            productDto = new ProductDto();
            productDto.setId(productId);
            productDto.setName(name);
            productDto.setCurrentPrice(priceResponse);

        } catch (Exception e) {
            throw new ProductNotFoundException(productId);
        }

        return productDto;
    }

    @Override
    public ProductDto updateProduct (ProductDto productDto)
    {
        if (productDto != null && productDto.getCurrentPrice() != null) {
            Price price = new Price(productDto);
            priceRepository.save(price);
            return productDto;
        }
        return null;
    }

    @Override
    public PriceDto getProductPrice (long productId)
    {
        Optional<Price> priceEntity = priceRepository.findById(productId);
        if (priceEntity.isPresent()) {
            Price price = priceEntity.get();
            return PriceDto.toDTO(price);
        }
        else {
            return null;
        }
    }

}
