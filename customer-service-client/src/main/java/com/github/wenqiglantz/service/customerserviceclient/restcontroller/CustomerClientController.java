package com.github.wenqiglantz.service.customerserviceclient.restcontroller;

import com.github.wenqiglantz.service.customerserviceclient.data.CustomerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerClientController {

    private final WebClient webClient;
    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @PostMapping("/customers")
    public Mono<CustomerVO> createCustomer(CustomerVO customerVO){
        return webClient.post()
                .uri("/customers")
                //.header("Authorization", "Bearer MY_SECRET_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerVO), CustomerVO.class)
                .retrieve()
                .bodyToMono(CustomerVO.class)
                .timeout(Duration.ofMillis(10_000))
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> Mono.just(CustomerVO.builder().build()));
                });
    }

    @GetMapping("/customers/{customerId}")
    public Mono<CustomerVO> getCustomer(@PathVariable String customerId) {
        return webClient
                .get().uri("/customers/" + customerId)
                .retrieve()
                .bodyToMono(CustomerVO.class)
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> Mono.just(CustomerVO.builder().build()));
                });
    }

    @PutMapping("/customers/{customerId}")
    public Mono<CustomerVO> updateCustomer(@PathVariable String customerId, CustomerVO customerVO){
        return webClient.put()
                .uri("/customers/" + customerVO.getCustomerId())
                //.header("Authorization", "Bearer MY_SECRET_TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerVO), CustomerVO.class)
                .retrieve()
                .bodyToMono(CustomerVO.class)
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> Mono.just(CustomerVO.builder().build()));
                });
    }

    @DeleteMapping("/customers/{customerId}")
    public Mono<String> deleteCustomer(@PathVariable String customerId){
        return webClient.delete()
                .uri("/customers/" + customerId)
                .retrieve()
                .bodyToMono(String.class)
                .transform(it -> {
                    ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("customer-service");
                    return rcb.run(it, throwable -> Mono.just(customerId));
                });
    }
}
