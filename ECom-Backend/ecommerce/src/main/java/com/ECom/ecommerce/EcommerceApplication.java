package com.ECom.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ECom.ecommerce.entities")
@EnableJpaRepositories("com.ECom.ecommerce.repositories")
public class EcommerceApplication {
		public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
