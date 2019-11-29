package com.test.clould;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test_route", p -> p
                        .path(true, "/test/{testId}/route")
                        .filters(f -> f
                                .setPath("/v1/testBackend")
                                .circuitBreaker(config -> config
                                        .setName("failed_request")))
                        .uri("http://localhost:9090/"))
                .build();
    }
}
