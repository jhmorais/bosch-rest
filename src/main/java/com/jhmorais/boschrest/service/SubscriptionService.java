package com.jhmorais.boschrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmorais.boschrest.model.Subscription;
import com.jhmorais.boschrest.repository.SubscriptionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final SubscriptionRepository subscriptionRepository;

    public Mono<Subscription> createSubscription(Subscription subscription){
        return subscriptionRepository.save(subscription);
    }

    public Flux<Subscription> getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }

    public Mono<Subscription> findById(String subscriptionId){
        return subscriptionRepository.findById(subscriptionId);
    }

    public Mono<Subscription> updateSubscription(String subscriptionId,  Subscription subscription){
        return subscriptionRepository.findById(subscriptionId)
                .flatMap(dbSubscription -> {
                	dbSubscription.setUserId(subscription.getUserId());
                    dbSubscription.setEvent(subscription.getEvent());
                    dbSubscription.setTargetUrl(subscription.getTargetUrl());
                    dbSubscription.setFilter(subscription.getFilter());
                    dbSubscription.setStatus(subscription.isStatus());
                    return subscriptionRepository.save(dbSubscription);
                });
    }

    public Mono<Subscription> deleteSubscription(String subscriptionId){
        return subscriptionRepository.findById(subscriptionId)
                .flatMap(existingSubscription -> subscriptionRepository.delete(existingSubscription)
                        .then(Mono.just(existingSubscription)));
    }

    public Flux<Subscription> fetchSubscriptions(String filter) {
        Query query = new Query()
                .with(Sort
                        .by(Collections.singletonList(Sort.Order.asc("target_url")))
                );
        query.addCriteria(Criteria
                .where("filter")
                .regex(filter)
        );

        return reactiveMongoTemplate
                .find(query, Subscription.class);
    }
}
