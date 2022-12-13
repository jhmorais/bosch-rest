package com.example.demo.controller;

import com.example.demo.model.Subscription;
import com.example.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("")
    public Flux<Subscription> list() {
        return subscriptionService.listAllSubscriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Subscription>> get(@PathVariable Integer id) {
        try {
            Mono<Subscription> subscription = subscriptionService.getSubscription(id);
            return new ResponseEntity<Mono<Subscription>>(subscription, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Mono<Subscription>>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    public void add(@RequestBody Subscription subscription) {
        subscriptionService.saveSubscription(subscription);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Subscription subscription, @PathVariable Integer id) {
        try {
            Mono<Subscription> existSubscription = subscriptionService.getSubscription(id);
            Subscription model = existSubscription.block();
            model.setEvent(subscription.getEvent());
            model.setFilter(subscription.getFilter());
            model.setStatus(subscription.isStatus());
            model.setTargetUrl(subscription.getTargetUrl());
            model.setUserId(subscription.getUserId());
            
            subscriptionService.saveSubscription(model);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        subscriptionService.deleteSubscription(id);
    }
}