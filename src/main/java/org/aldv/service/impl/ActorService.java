package org.aldv.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.aldv.repository.ActorRepository;

@ApplicationScoped
public class ActorService {

    @Inject
    ActorRepository repository;

//    public Actor findById(Integer id){
//        ;
//    }
//
//    public List<Actor> findAll(){
//
//    }
}
