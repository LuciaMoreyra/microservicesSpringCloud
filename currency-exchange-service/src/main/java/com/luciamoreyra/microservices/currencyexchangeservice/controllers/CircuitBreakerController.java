package com.luciamoreyra.microservices.currencyexchangeservice.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    @Bulkhead(name = "default")
    @RateLimiter(name = "default")
    public String sampleApiRetry(){
        logger.info("sample api received");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://some-url", String.class);
//        return forEntity.getBody();
        return "sample api";
    }

    public String hardcodedResponse(Exception e){
        return "Fallback response";
    }

    @GetMapping("/sample-api-2")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse2")
    public String sampleApiCircuitBreaker(){
        logger.info("sample api received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://some-url", String.class);
        return forEntity.getBody();
    }

    public String hardcodedResponse2(Exception e){
        return "Fallback response 2";
    }
}
