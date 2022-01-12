package com.uwindsor.reccomendationsystem.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.uwindsor.reccomendationsystem.dao.AppUser;
import com.uwindsor.reccomendationsystem.dao.Picture;
import com.uwindsor.reccomendationsystem.model.Actor;
import com.uwindsor.reccomendationsystem.model.Credit;
import com.uwindsor.reccomendationsystem.model.Movie;
import com.uwindsor.reccomendationsystem.repository.ActorRepo;
import com.uwindsor.reccomendationsystem.repository.CreditRepo;
import com.uwindsor.reccomendationsystem.repository.MovieRepo;
import com.uwindsor.reccomendationsystem.repository.PictureRepository;
import com.uwindsor.reccomendationsystem.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class MainController {

	@Autowired
	MovieRepo movieRepo;

	@Autowired
	CreditRepo creditRepo;

	@Autowired
	MongoTemplate mongotemplate;

	@Autowired
	ActorRepo actorrepo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PictureRepository pictureRepository;

	@PostMapping("/users/login")
	synchronized public ResponseEntity<?> getRequest(@RequestBody AppUser user, HttpServletResponse response)
			throws Exception {

		List<AppUser> users = userRepository.findByEmailaddress(user.getEmailaddress());
		String Usertype = "Failure";

		for (AppUser other : users) {
			if (other.getEmailaddress().equalsIgnoreCase(user.getEmailaddress())) {
				if (other.getPassword().equals(user.getPassword())) {

					return ResponseEntity.ok(HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(Usertype, HttpStatus.CONFLICT);
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/users/register", consumes = "multipart/form-data")
	synchronized public ResponseEntity<String> createNewObjectWithImage(@RequestParam("model") String myParams,
			@RequestParam(value = "cover_image", required = false) MultipartFile multipartfile) {

		JsonParser jsonParser = new JsonParser();
		JsonObject object = (JsonObject) jsonParser.parse(myParams);
		AppUser user = new AppUser();

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);

		user.setId(strDate);
		user.setFirst_name(object.get("first_name").getAsString());
		user.setLast_name(object.get("last_name").getAsString());
		user.setEmailaddress(object.get("emailaddress").getAsString());
		user.setPassword(object.get("password").getAsString());
		user.setPhone(object.get("phone").getAsString());

		List<AppUser> users = userRepository.findByEmailaddress(user.getEmailaddress());

		if (!users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);// 409
		} else {
			AppUser newuser = userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}

	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/actor", consumes = "multipart/form-data")
	synchronized private ResponseEntity<String> addActor(@RequestParam("model") String myParams) {

		JsonParser jsonParser = new JsonParser();
		JsonObject object = (JsonObject) jsonParser.parse(myParams);
		Actor actor = new Actor();

		actor.setName(object.get("actor").getAsString().toLowerCase());
		actor.setRating(object.get("rating").getAsInt());
		actor.setType(object.get("type").getAsString().toLowerCase());

		List<Actor> retrievedActor = getActor(actor.getName(), actor.getType());

		if (retrievedActor != null && retrievedActor.size() == 1) {
			actor.setId(retrievedActor.get(0).getId());

		}
		actorrepo.save(actor);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	private List<Actor> getActor(String actor, String type) {
		Query query = new Query();
		List<Criteria> criteria = new ArrayList<>();
		criteria.add(Criteria.where("name").is(actor.toLowerCase()));

		criteria.add(Criteria.where("type").is(type.toLowerCase()));

		query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		List<Actor> retrievedActor = mongotemplate.find(query, Actor.class);
		return retrievedActor;

	}

//	@GetMapping("/get")
//	private List<Credit> getActor1(String actor, String type) {
//		
//		//System.out.println(creditRepo.findByName("Sam Worthington"));
//		return creditRepo.findAll();
//		
//	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/picture", consumes = "multipart/form-data")
	synchronized public ResponseEntity<String> createPicture(@RequestParam("model") String myParams,
			@RequestParam(value = "cover_image", required = false) MultipartFile multipartfile) {

		JsonParser jsonParser = new JsonParser();
		String json = null;
		JsonObject object = (JsonObject) jsonParser.parse(myParams);
		Picture user = new Picture();

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);

		user.setId(strDate);
		user.setPicture_name(object.get("Picture_name").getAsString());
		user.setActor(object.get("Actor").getAsString());
		user.setDirector(object.get("Director").getAsString());
		user.setBudget(object.get("Budget").getAsString());
		user.setGeneres(object.get("genre").getAsString());
		user.setReleaseDay(object.get("releaseday").getAsString());
		int a = 0;
		int b = 0;
		int c = 0;

		// Rate and return the result
		if (user.getGeneres().equalsIgnoreCase("action") || user.getGeneres().equalsIgnoreCase("Love & Adventure")
				|| user.getGeneres().equalsIgnoreCase("Adventure")
				|| user.getGeneres().equalsIgnoreCase("Action & Love")) {
			a = 3;
		}

		if (user.getGeneres().equalsIgnoreCase("Animation") || user.getGeneres().equalsIgnoreCase("Dance")
				|| user.getGeneres().equalsIgnoreCase("Romantic")
				|| user.getGeneres().equalsIgnoreCase("Comedy & Romantic")) {
			a = 2;
		}

		if (user.getGeneres().equalsIgnoreCase("Drama") || user.getGeneres().equalsIgnoreCase("Comedy")
				|| user.getGeneres().equalsIgnoreCase("Drama & Romantic")) {
			a = 1;
		}

		if (user.getReleaseDay().equalsIgnoreCase("week day")) {
			b = 1;
		} else {
			b = 3;
		}

		float budget = Float.parseFloat(user.getBudget());
		if (budget <= 0.5) {
			c = 1;
		} else if (budget > 0.5 && budget < 2) {
			c = 2;
		} else {
			c = 3;
		}

//		int count=0;
//		String[] actorlist = user.getActor().split(",");
//		HashMap<String, String> map = new HashMap<String, String>();
//		int noOfPastMoviesToGet = 1;
//		for (String actor : actorlist) {
//			List<String> movie_id = new ArrayList<String>();
//			movie_id = getMoviesID(actor.trim(), noOfPastMoviesToGet );
//			System.out.println(movie_id.toString());
//			float popularity = 0;
//			if(count==5)
//			{
//				break;
//			}
//			count++;
//			for (String movieId : movie_id) {
//				List<Movie> list = getMovie(movieId);
//				String p = list.get(0).getPopularity();
//				try {
//				
//				popularity = popularity + Float.parseFloat(p);
//				}
//				catch (Exception e)
//				{
//					System.out.println("skip this exception");
//				}
//					
//				
//			}
//			
//			popularity = popularity/noOfPastMoviesToGet;
//			map.put(actor, Float.toString(popularity));
//		}
		// String json

		/*
		 * ObjectMapper objectMapper = new ObjectMapper(); try { json =
		 * objectMapper.writeValueAsString(map); System.out.println(json); } catch
		 * (JsonProcessingException e) { e.printStackTrace(); }
		 */
		// convert the map to the json object and return
		// OR
		// save this data in another collection and get it on the same page

		List<Actor> retrievedActor = getActor(user.getActor(), "actor");
		int d = retrievedActor.isEmpty() ? 0 : retrievedActor.get(0).getRating();

		List<Actor> retrieveddirector = getActor(user.getDirector(), "director");
		int e = retrieveddirector.isEmpty() ? 0 : retrieveddirector.get(0).getRating();

		int result = a + b + c + d + e;
		String status = "";
		if (result <= 8) {
			status = "fail";
//		} else if (result > 5 && result < 8) {
//			status = "moderate";
		} else {
			status = "blockbuster";
		}

		Picture newuser = pictureRepository.save(user);

		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/popularity")
	synchronized public ResponseEntity<List<List<String>>> createpopularity(@RequestParam("model") String myParams) {
//
//		JsonParser jsonParser = new JsonParser();
//		String json = null;
//		System.out.println(myParams);
//		JsonObject object = (JsonObject) jsonParser.parse(myParams);
//		

		String actor1 = myParams;
		String finalstring = null;
//		actor1 = actor1.substring(1, actor1.length()-1);
		int count = 0;
		String[] actorlist = actor1.split(",");
		List<List<String>> listOfLists = new ArrayList<>();

		List<String> cd = new ArrayList<String>();
		HashMap<String, String> map = new HashMap<String, String>();
		int noOfPastMoviesToGet = 1;
		for (String actor : actorlist) {
			float popularity = 0;
			List<Actor> retrievedActor = getActor(actor.trim(), "actor");
			if (retrievedActor.isEmpty()) {
				List<String> movie_id = new ArrayList<String>();
				movie_id = getMoviesID(actor.trim(), noOfPastMoviesToGet);
				System.out.println(movie_id.toString());

				if (count == 5) {
					break;
				}
				count++;
				for (String movieId : movie_id) {
					List<Movie> list = getMovie(movieId);
					String p = list.get(0).getPopularity();
					try {

						popularity = popularity + Float.parseFloat(p);
					} catch (Exception e) {
						System.out.println("skip this exception");
					}

				}

			} else {
				popularity = ((retrievedActor.get(0).getRating())*100)/10;
			}

			popularity = popularity / 2;
			map.put(actor.trim(), Float.toString(popularity));
			finalstring = finalstring + actor.trim() + ";" + Float.toString(popularity);
			List<String> ab = new ArrayList<String>();
			ab.add(actor.trim());
			ab.add(Float.toString(popularity));

			listOfLists.add(ab);
		}

		// String json

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(map);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// convert the map to the json object and return
		// OR
		// save this data in another collection and get it on the same page

		return new ResponseEntity<>(listOfLists, HttpStatus.OK);

	}

	public List<Movie> getMovie(String id) {
		Query query = new Query();
		List<Criteria> criteria = new ArrayList<>();
		criteria.add(Criteria.where("id").is(id));

		query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		List<Movie> retrievedActor = mongotemplate.find(query, Movie.class);
		return retrievedActor;

	}

	@GetMapping("/get")
	public List<String> getMoviesID(String name, int noOfMovies) {
		// List<Movie> list = movieRepo.findAll();
		List<String> finalList = new ArrayList();

		// List<Credit> abc = creditRepo.findByName("Sam Worthington");
		List<Credit> list2 = creditRepo.findAll();

		for (Credit credit : list2) {
			boolean movieAdded = false;
			if (finalList.size() < noOfMovies) {
				String str = credit.getCast();
				JsonParser parser = new JsonParser();
				JsonArray array = (JsonArray) parser.parse(str);

				for (JsonElement ar : array) {

					JsonObject objStr = ar.getAsJsonObject();

					if (!movieAdded) {
						if (objStr.get("name").getAsString().equalsIgnoreCase(name)) {
							finalList.add(credit.getMovie_id());
							movieAdded = true;
						}
						System.out.println(objStr);
					} else {
						break;
					}

				}

			}
		}
		return finalList;
	}

}