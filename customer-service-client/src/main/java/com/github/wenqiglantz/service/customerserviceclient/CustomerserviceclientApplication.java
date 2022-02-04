package com.github.wenqiglantz.service.customerserviceclient;

import com.github.wenqiglantz.service.customerserviceclient.config.CircuitBreakerConfiguration;
import com.github.wenqiglantz.service.customerserviceclient.config.WebClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebClientConfiguration.class, CircuitBreakerConfiguration.class})
public class CustomerserviceclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerserviceclientApplication.class, args);
	}
}
