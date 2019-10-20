package com.target.myretail;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.controller.ProductController;
import com.target.myretail.dto.PriceDto;
import com.target.myretail.dto.ProductDto;
import com.target.myretail.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTests {

    private MockMvc           mvc;
    private ObjectMapper      mapper;
    @Mock
    private ProductService    productService;
    @InjectMocks
    private ProductController productController;

    @Before
    public void init () {
        this.mvc = MockMvcBuilders.standaloneSetup(this.productController).build();
        MockitoAnnotations.initMocks(this);
        this.mapper = new ObjectMapper(new JsonFactory());
    }

    @Test
    public void verifyGetProductInfo () throws Exception {

        PriceDto mockPrice = new PriceDto();
        mockPrice.setValue(new BigDecimal("14.99"));
        mockPrice.setCurrencyCode("$");

        long productId=12345;
        ProductDto mockProduct=new ProductDto();
        mockProduct.setId(productId);
        mockProduct.setName("The Big Lebowski (Blu-ray) (Widescreen)");
        mockProduct.setCurrentPrice(mockPrice);


        when(productService.getProductInfo(productId)).thenReturn(mockProduct);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/products/"+productId);
        MvcResult result = this.mvc.perform(builder).andDo(print()).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        ProductDto productResponse = mapper.readValue(content, new TypeReference<ProductDto>() {
        });

        assert (productResponse.getName().equals(mockProduct.getName()));
        assert (productResponse.getCurrentPrice().getValue().equals(mockPrice.getValue()));
        assert (productResponse.getCurrentPrice().getCurrencyCode().equals(mockPrice.getCurrencyCode()));
    }

    @Test
    public void verifyGetProductPrice () throws Exception {

        PriceDto mockPrice = new PriceDto();
        mockPrice.setValue(new BigDecimal("14.99"));
        mockPrice.setCurrencyCode("$");

        long productId=12345;


        when(productService.getProductPrice(productId)).thenReturn(mockPrice);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/products/"+productId+"/price");
        MvcResult result = this.mvc.perform(builder).andDo(print()).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        PriceDto productResponse = mapper.readValue(content, new TypeReference<PriceDto>() {
        });

        assert (productResponse.getValue().equals(mockPrice.getValue()));
        assert (productResponse.getCurrencyCode().equals(mockPrice.getCurrencyCode()));
    }

    @Test
    public void verifyUpdatePrice() throws Exception {

        PriceDto mockPrice = new PriceDto();
        mockPrice.setValue(new BigDecimal("14.99"));
        mockPrice.setCurrencyCode("$");

        long productId=12345;
        ProductDto mockProduct=new ProductDto();
        mockProduct.setId(productId);
        mockProduct.setName("The Big Lebowski (Blu-ray) (Widescreen)");
        mockProduct.setCurrentPrice(mockPrice);

        when(productService.updateProduct(mockProduct)).thenReturn(mockProduct);

        this.mvc.perform(put("/api/v1/products/"+productId)
                .content(this.mapper.writeValueAsString(mockProduct))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}