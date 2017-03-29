package com.royalrangers.controller;

import com.royalrangers.bean.ResponseResult;
import com.royalrangers.model.Subscriber;
import com.royalrangers.repository.SubscriberRepository;
import com.royalrangers.service.SubscribeService;
import com.royalrangers.utils.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;

    @PostMapping
    public ResponseResult subscribe(@RequestBody String email) {

        log.info("Add subscriber: " + email);
        try {
            subscribeService.add(email);
            return ResponseBuilder.success(String.format("Email %s added to subscribers list", email));
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseResult unsubscribe(@RequestBody String email) {

        log.info("Remove subscriber: " + email);
        try {
            subscribeService.remove(email);
            return ResponseBuilder.success(String.format("Email %s removed from subscribers list", email));
        } catch (Exception e) {
            return ResponseBuilder.fail(e.getMessage());
        }
    }
}
