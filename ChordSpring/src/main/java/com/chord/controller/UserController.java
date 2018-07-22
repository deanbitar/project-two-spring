package com.chord.controller;

import java.util.List;
import java.util.Set;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chord.dao.UserDao;
import com.chord.model.User;
import com.chord.util.Hash;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	@Autowired
	private UserDao userDao;
	
	public UserController() {
		
	}
	
	@GetMapping(value="/createUser.chord")
	public @ResponseBody User createNewUser(String firstname, String lastname, String email,
			String dob, String password, String genreOne, String genreTwo, String genreThree) {
		
		System.out.println("Create request with firstname: " + firstname + " last name " + lastname);
		
		User user = new User();
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setDob(dob);
		user.setPassword(Hash.sha256(password));
		user.setGenreOne(genreOne);
		user.setGenreTwo(genreTwo);
		user.setGenreThree(genreThree);
		
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
	public @ResponseBody Set<User> getFriendsOfUser(int userId) {
		Set<User> friends = null;
		
		friends = userDao.selectById(userId).getFriends();
		
		return friends;
	}
	
	@PostMapping(value="/login.chord")
	public @ResponseBody User login(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(request.getParameterNames().nextElement());
		
		JSONObject object = new JSONObject(request.getParameterNames().nextElement());
		
		String email = object.getString("email");
		String password = object.getString("password");
		
		System.out.println("New login request with " + email + " " + password);
		
		User user = userDao.selectByEmail(email);
		
		String hashPass = Hash.sha256(password);
		
		if(user == null)
			return null;
		else if(!user.getPassword().equals(hashPass))
			return null;
		else {
			return user;
		}
	}
	
	@GetMapping("/searchUserByName.chord")
	public @ResponseBody List<User> searchUsers(String name) {
		return userDao.searchByName(name);
	}
}
