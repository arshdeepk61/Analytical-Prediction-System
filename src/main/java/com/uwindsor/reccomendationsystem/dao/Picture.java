package com.uwindsor.reccomendationsystem.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection ="picture")
public class Picture {
   
	  @Id
	private String id;
	private String picture_name;
    private String actor;
    private String Director;
    private String Budget;
    private String rating;
    private String releaseDay;
    private String generes;
   
    
	public Picture() {
		super();
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", picture_name=" + picture_name + ", actor=" + actor + ", Director=" + Director
				+ ", releaseDay=" + releaseDay + ", generes=" + generes + ", Budget=" + Budget + ", rating=" + rating
				+ "]";
	}

	public Picture(String id, String picture_name, String actor, String director, String releaseDay, String generes,
			String budget, String rating) {
		super();
		this.id = id;
		this.picture_name = picture_name;
		this.actor = actor;
		Director = director;
		this.releaseDay = releaseDay;
		this.generes = generes;
		Budget = budget;
		this.rating = rating;
	}

	public String getReleaseDay() {
		return releaseDay;
	}

	public void setReleaseDay(String releaseDay) {
		this.releaseDay = releaseDay;
	}

	public String getGeneres() {
		return generes;
	}

	public void setGeneres(String generes) {
		this.generes = generes;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicture_name() {
		return picture_name;
	}
	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getDirector() {
		return Director;
	}
	public void setDirector(String director) {
		Director = director;
	}
	public String getBudget() {
		return Budget;
	}
	public void setBudget(String budget) {
		Budget = budget;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	

    

}
