package io.reflectoring.springwebflux.repository;

import io.reflectoring.springwebflux.model.Subscription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {
}
