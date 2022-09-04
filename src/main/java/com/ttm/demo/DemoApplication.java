package com.ttm.demo;

import com.ttm.demo.dao.repository.ProductRepository;
import com.ttm.demo.dao.service.ProductService;
import com.ttm.demo.domaine.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@Slf4j
public class DemoApplication {

	@Value("${spring.profiles.active:}")
	public String profile;

	@Value("${name:zali}")
	public String name;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner start(ProductRepository repository){
		return args -> {
			repository.save(Product.builder()
					.price(new BigDecimal("123.05"))
					.label("Pomme")
					.build());
			log.info("Test {} {}", profile, name);
			log.info("Product: {}", repository.findById(1L).map(Product::getLabel).orElse(""));
		};
	}

}
