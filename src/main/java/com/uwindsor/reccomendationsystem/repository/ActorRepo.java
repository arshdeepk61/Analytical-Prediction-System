package com.uwindsor.reccomendationsystem.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.uwindsor.reccomendationsystem.model.Actor;

public interface ActorRepo extends MongoRepository<Actor, ObjectId> {

}
