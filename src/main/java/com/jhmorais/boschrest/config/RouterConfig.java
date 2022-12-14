package com.jhmorais.boschrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jhmorais.boschrest.handler.SubscriptionHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(SubscriptionHandler handler) {
        return route(GET("/handler/subscriptions").and(accept(MediaType.APPLICATION_JSON)), handler::getAllSubscriptions)
                .andRoute(GET("/handler/subscriptions/{subscriptionId}").and(contentType(MediaType.APPLICATION_JSON)), handler::getSubscriptionById)
                .andRoute(POST("/handler/subscriptions").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(PUT("/handler/subscriptions/{subscriptionId}").and(contentType(MediaType.APPLICATION_JSON)), handler::updateSubscriptionById)
                .andRoute(DELETE("/handler/subscriptions/{subscriptionId}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteSubscriptionById);
    }
}
