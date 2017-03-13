package com.royalrangers.controller;

import com.royalrangers.model.Subscriber;
import com.royalrangers.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscribeController {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @RequestMapping("/create")
    public Subscriber create(String email) {

        Subscriber subscriber = new Subscriber(email);
        subscriberRepository.save(subscriber);

        return subscriber;
    }
}
