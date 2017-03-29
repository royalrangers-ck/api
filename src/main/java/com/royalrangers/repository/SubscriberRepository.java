package com.royalrangers.repository;

import com.royalrangers.model.Subscriber;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

    Subscriber findByEmail(String email);

    void deleteByEmail(String email);
}
