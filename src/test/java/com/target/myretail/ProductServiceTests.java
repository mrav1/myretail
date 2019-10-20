package com.target.myretail;

import com.target.myretail.dto.PriceDto;
import com.target.myretail.entity.Price;
import com.target.myretail.repository.PriceRepository;
import com.target.myretail.service.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    public void verifyGetPrice () {

        Price price = new Price();
        price.setValue(new BigDecimal(14.99));
        price.setCurrencyCode("USD");

        when(priceRepository.findById(13860428L)).thenReturn(java.util.Optional.of(price));
        PriceDto priceDto = productService.getProductPrice(13860428L);

        assert (priceDto.getValue().equals(new BigDecimal(14.99)));
        assert (priceDto.getCurrencyCode().equals("USD"));
    }
}
