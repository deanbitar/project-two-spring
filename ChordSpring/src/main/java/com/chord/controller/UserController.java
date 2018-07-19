package com.chord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chord.dao.UserDao;
import com.chord.model.User;

import oracle.net.aso.u;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;
	
	public UserController() {
		
	}
	
	@PostMapping("/createUser.chord")
	public String createNewUser(String firstname, String lastname, String email,
			String dob, String password, String genreOne, String genreTwo,String genreThree) {
		
		User user = new User();
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setDob(dob);
		user.setPassword(password);
		user.setGenreOne(genreOne);
		user.setGenreTwo(genreTwo);
		user.setGenreThree(genreThree);
		
		userDao.insert(user);
		user = userDao.selectByEmail(email);
		
		if(user == null) {
			return " ";
		}
		else
		{
			return " ";
		}
	}
}
