package com.jhmorais.boschrest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.jhmorais.boschrest.model.Subscription;

@Repository
public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {
}
