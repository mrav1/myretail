package com.target.myretail;

import com.target.myretail.entity.Price;
import com.target.myretail.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@EnableCaching
@SpringBootApplication
public class MyRetailApplication {

	public static final Logger LOG = LoggerFactory.getLogger(MyRetailApplication.class);

	public static void main(String[] args) {
		LOG.info("Starting MyRetailApplication...");
		SpringApplication.run(MyRetailApplication.class, args);
	}

	/***
	 * Update DB with price for product id 13860428 on application start
	 *
	 * @param priceRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner initDatabase (PriceRepository priceRepository) {
		return args -> {
			LOG.info("Initializing database");

			Price productPrice = new Price();
			productPrice.setProductId(13860428L);
			productPrice.setValue(new BigDecimal("14.99"));
			productPrice.setCurrencyCode("USD");
			priceRepository.save(productPrice);

			LOG.info("Access http://localhost:8080/myretail/api/v1/products/13860428");
		};
	}

}
