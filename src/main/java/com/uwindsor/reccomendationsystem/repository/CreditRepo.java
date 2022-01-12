package com.uwindsor.reccomendationsystem.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uwindsor.reccomendationsystem.model.Credit;

@Repository
public interface CreditRepo extends MongoRepository<Credit, ObjectId> {

	@org.springframework.data.mongodb.repository.Query(value = "{ 'name' : ?0 }" , fields= "{ 'cast.name' : 0 }")
	List<Credit>findByName(String name);

}
