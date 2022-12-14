package io.reflectoring.springwebflux.controller;

import io.reflectoring.springwebflux.model.Subscription;
import io.reflectoring.springwebflux.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Subscription> create(@RequestBody Subscription subscription){
        return subscriptionService.createSubscription(subscription);
    }

    @GetMapping
    public Flux<Subscription> getAllSubscriptions(){
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{subscriptionId}")
    public Mono<ResponseEntity<Subscription>> getSubscriptionById(@PathVariable String subscriptionId){
        Mono<Subscription> subscription = subscriptionService.findById(subscriptionId);
        return subscription.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{subscriptionId}")
    public Mono<ResponseEntity<Subscription>> updateSubscriptionById(@PathVariable String subscriptionId, @RequestBody Subscription subscription){
        return subscriptionService.updateSubscription(subscriptionId,subscription)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{subscriptionId}")
    public Mono<ResponseEntity<Void>> deleteSubscriptionById(@PathVariable String subscriptionId){
        return subscriptionService.deleteSubscription(subscriptionId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public Flux<Subscription> searchSubscriptions(@RequestParam("name") String name) {
        return subscriptionService.fetchSubscriptions(name);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Subscription> streamAllSubscriptions() {
        return subscriptionService
                .getAllSubscriptions()
                .flatMap(subscription -> Flux
                        .zip(Flux.interval(Duration.ofSeconds(2)),
                                Flux.fromStream(Stream.generate(() -> subscription))
                        )
                        .map(Tuple2::getT2)
                );
    }
}
