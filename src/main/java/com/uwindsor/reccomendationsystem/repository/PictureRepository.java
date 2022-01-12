package com.uwindsor.reccomendationsystem.repository;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uwindsor.reccomendationsystem.dao.AppUser;
import com.uwindsor.reccomendationsystem.dao.Picture;


public interface PictureRepository extends MongoRepository<Picture, Long> {

	
//	List<AppUser> findByEmailaddress(String emailaddress);
//	List<AppUser> findAllByusertype(String usertype);
//	List<AppUser> findAllById(Long id);

	
}
