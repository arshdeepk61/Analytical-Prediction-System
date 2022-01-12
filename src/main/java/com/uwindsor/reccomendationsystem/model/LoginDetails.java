//package com.uwindsor.reccomendationsystem.model;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Component;
//
//import com.uwindsor.reccomendationsystem.dao.User;
//
//
//@Component
//public class LoginDetails {
//
//	User user = new User();
//
//	public List<User> getUserdata() throws Exception {
//		return null;
//
////		List<User> list=new ArrayList<User>();  
////		 return (List<User>) jdbcTemplate.query(Queries.Loginsql,new ResultSetExtractor<List<User>>(){  
////			    public List<User> extractData(ResultSet rs) throws SQLException,  
////			            DataAccessException {  
////			      
////			        
////			        while(rs.next()){  
////			        User e=new User();  
////			        e.setEmailid(rs.getString(1));  
////			        e.setName(rs.getString(2));  
////			        e.setDob(rs.getString(3)); 
////			        e.setPassword(rs.getString(4));
////			        list.add(e);  
////			        }  
////			        return list;  
////			        }  
////			    });
//	}
//
//}
