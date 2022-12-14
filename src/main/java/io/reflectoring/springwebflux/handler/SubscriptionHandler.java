package io.reflectoring.springwebflux.handler;

import io.reflectoring.springwebflux.model.Subscription;
import io.reflectoring.springwebflux.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SubscriptionHandler {

    private final SubscriptionService subscriptionService;

    public Mono<ServerResponse> getAllSubscriptions(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(subscriptionService.getAllSubscriptions(), Subscription.class);
    }

    public Mono<ServerResponse> getSubscriptionById(ServerRequest request) {
        return subscriptionService
                .findById(request.pathVariable("subscriptionId"))
                .flatMap(subscription -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(subscription, Subscription.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Subscription> subscription = request.bodyToMono(Subscription.class);

        return subscription
                .flatMap(u -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(subscriptionService.createSubscription(u), Subscription.class)
                );
    }

    public Mono<ServerResponse> updateSubscriptionById(ServerRequest request) {
        String id = request.pathVariable("subscriptionId");
        Mono<Subscription> updatedSubscription = request.bodyToMono(Subscription.class);

        return updatedSubscription
                .flatMap(u -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(subscriptionService.updateSubscription(id, u), Subscription.class)
                );
    }

    public Mono<ServerResponse> deleteSubscriptionById(ServerRequest request){
        return subscriptionService.deleteSubscription(request.pathVariable("subscriptionId"))
                .flatMap(u -> ServerResponse.ok().body(u, Subscription.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
