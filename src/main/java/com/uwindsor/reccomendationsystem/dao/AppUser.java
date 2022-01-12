package com.uwindsor.reccomendationsystem.dao;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="users")
public class AppUser {
     
	  @Transient
	    public static final String SEQUENCE_NAME = "users_sequence";
	  @Id
     private String id;
    @Override
	public String toString() {
		return "AppUser [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", emailaddress="
				+ emailaddress + ", password=" + password + ", phone=" + phone + "]";
	}


	public AppUser(String id, String first_name, String last_name, String emailaddress, String password, String phone) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.emailaddress = emailaddress;
		this.password = password;
		this.phone = phone;
	}

	private String first_name;
    private String last_name;
    private String emailaddress;
    private String password;
    private String phone;

    public AppUser(){

    }

 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
