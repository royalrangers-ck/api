package com.royalrangers.service;


import com.royalrangers.model.Subscriber;
import com.royalrangers.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubscribeService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public void add(String email) {
        if (subscriberRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("This email already exist in subscribers list");
        } else {
            Subscriber subscriber = new Subscriber(new Date(), new Date(),email);
            subscriberRepository.save(subscriber);
        }
    }

    public void remove(String email) {
        if (subscriberRepository.findByEmail(email) == null) {
            throw new IllegalArgumentException("This email does not exist in subscribers list");
        } else {
            subscriberRepository.deleteByEmail(email);
        }
    }
}
