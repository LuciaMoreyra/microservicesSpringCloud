package com.luciamoreyra.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/*
* here we will customiza gateway routes
 */

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
              .route(p -> p
                      // matching with the request by a path
                      .path("/get")
                      // once the request is matched -> do something with it
                      .filters(f-> f
                              .addRequestHeader("myheader", "myURI")
                              .addRequestParameter("param", "value")
                      )
                      .uri("http://httpbin.org/80")
              )
                .route(p -> p
                        .path("/currency-exchange/**")
                        .uri("lb://currency-exchange") // load balance
                )
                .route(p -> p
                        .path("/currency-conversion/**")
                        .uri("lb://currency-conversion") // load balance
                )
                .route(p -> p
                        .path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion") // load balance
                )
                .route(p -> p
                        .path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}")
                        )
                        .uri("lb://currency-conversion") // load balance
                )
              .build();
    }
}
