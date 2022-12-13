package com.example.demo.repository;

import com.example.demo.model.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, Integer>{}
