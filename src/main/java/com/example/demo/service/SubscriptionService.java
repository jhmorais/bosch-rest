package com.example.demo.service;

import com.example.demo.model.Subscription;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository SubscriptionRepository;
    
    public Flux<Subscription> listAllSubscriptions() {
        return SubscriptionRepository.findAll();
    }

    public Mono<Subscription> saveSubscription(Subscription Subscription) {
        return SubscriptionRepository.save(Subscription);
    }

    public Mono<Subscription> getSubscription(Integer id) {
        return SubscriptionRepository.findById(id);
    }

    public Mono<Void> deleteSubscription(Integer id) {
        return SubscriptionRepository.deleteById(id);
    }
}
