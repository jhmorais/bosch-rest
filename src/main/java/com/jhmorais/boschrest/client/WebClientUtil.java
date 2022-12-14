package com.jhmorais.boschrest.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.jhmorais.boschrest.model.Subscription;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClient webClient;

    public WebClient.ResponseSpec getFakeUsers() {
        return webClient
                .get()
                .uri("https://randomuser.me/api/")
                .retrieve();
    }

    public Mono<Subscription> postUser(Subscription user) {
        return webClient
                .post()
                .uri("http://localhost:9000/api/users")
                .header("Authorization", "Basic MY_PASSWORD")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), Subscription.class)
                .retrieve()
                .bodyToMono(Subscription.class)
                .log()
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)))
                .onErrorReturn(new Subscription())
                .doOnError(throwable -> log.error("Result error out for POST user", throwable))
                .doFinally(signalType -> log.info("Result Completed for POST User: {}", signalType));
    }
}
