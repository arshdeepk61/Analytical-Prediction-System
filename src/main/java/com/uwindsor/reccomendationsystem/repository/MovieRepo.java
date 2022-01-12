package com.uwindsor.reccomendationsystem.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uwindsor.reccomendationsystem.model.Movie;

@Repository
public interface MovieRepo extends MongoRepository<Movie, ObjectId> {

}
