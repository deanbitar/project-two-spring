package com.chord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chord.dao.UserDao;
import com.chord.model.User;

@Controller
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	@Autowired
	private UserDao userDao;
	
	public UserController() {
		
	}
	
	@GetMapping(value="/createUser.chord")
	public @ResponseBody User createNewUser(String firstname, String lastname, String email,
			String dob, String password, String genreOne) {
		
		User user = new User();
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setDob(dob);
		user.setPassword(password);
		user.setGenreOne(genreOne);
		
		System.out.println("Adding user: " + user);
		
		userDao.insert(user);
		
		return user;
	}
	
	@GetMapping(value="/getUser.chord")
	public @ResponseBody User getUser(int userId) {
		System.out.println("getting user: " + userId);
		return userDao.selectById(userId);
	}
	
	@GetMapping(value="/addFriend.chord")
	@ResponseBody
	public String addFreind(int userId, int friendId) {
		
		User user = userDao.selectById(userId);
		User friend = userDao.selectById(friendId);
		
		user.getFriends().add(friend);
		userDao.update(user);
		
		return "{\"status\" : \"ok\"}";
	}
	
	@GetMapping(value="/getUserFriends.chord")
	public @ResponseBody List<User> getFriendsOfUser(int userId) {
		List<User> friends = null;
		
		friends = userDao.selectById(userId).getFriends();
		
		return friends;
	}
	
	@PostMapping(value="/login.chord")
	public @ResponseBody User login(String email, String password) {
		
		User user = userDao.selectByEmail(email);
		
		if(user == null)
			return null;
		else if(user.getPassword() != password)
			return null;
		else {
			return user;
		}
	}
}
