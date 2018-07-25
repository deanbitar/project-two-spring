package com.chord.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chord.dao.UserDao;
import com.chord.model.User;
import com.chord.util.Email;
import com.chord.util.Hash;
import com.chord.util.PassGen;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@MultipartConfig
public class UserController {

	@Autowired
	private UserDao userDao;

	public UserController() {

	}

	@GetMapping(value = "/createUser.chord")
	public @ResponseBody User createNewUser(String firstname, String lastname, String email, String dob,
			String password, String genreOne, String genreTwo, String genreThree) {

		System.out.println("Create request with firstname: " + firstname + " last name " + lastname);

		User user = new User();

		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setDob(dob);
		user.setPassword(Hash.sha256(password));
		user.setPicture("https://image.ibb.co/jqsPDd/profile.jpg");
		user.setGenreOne(genreOne);
		user.setGenreTwo(genreTwo);
		user.setGenreThree(genreThree);

		System.out.println("Adding user: " + user);

		userDao.insert(user);

		return user;
	}

	@GetMapping(value = "/getUser.chord")
	public @ResponseBody User getUser(int userId) {
		System.out.println("getting user: " + userId);
		return userDao.selectById(userId);
	}

	@GetMapping(value = "/addFriend.chord")
	@ResponseBody
	public String addFreind(int userId, int friendId) {

		User user = userDao.selectById(userId);
		User friend = userDao.selectById(friendId);

		user.getFriends().add(friend);
		userDao.update(user);

		return "{\"status\" : \"ok\"}";
	}

	@GetMapping(value = "/getUserFriends.chord")
	public @ResponseBody Set<User> getFriendsOfUser(int userId) {
		Set<User> friends = null;

		friends = userDao.selectById(userId).getFriends();

		return friends;
	}

	@PostMapping(value = "/login.chord")
	public @ResponseBody User login(HttpServletRequest request, HttpServletResponse response) {

		System.out.println(request.getParameterNames().nextElement());

		JSONObject object = new JSONObject(request.getParameterNames().nextElement());

		String email = object.getString("email");
		String password = object.getString("password");

		System.out.println("New login request with " + email + " " + password);

		User user = userDao.selectByEmail(email);

		String hashPass = Hash.sha256(password);

		if (user == null)
			return null;
		else if (!user.getPassword().equals(hashPass))
			return null;
		else {
			return user;
		}
	}

	@GetMapping("/searchUserByName.chord")
	public @ResponseBody List<User> searchUsers(String name) {
		return userDao.searchByName(name);
	}

	@GetMapping("/updateUser.chord")
	public @ResponseBody User updateUser(int userId, String email, String dob, String bio, String picture,
			String genreOne, String genreTwo, String genreThree) {

		User user = userDao.selectById(userId);

		user.setEmail(email);
		user.setDob(dob);
		user.setBio(bio);
		user.setPicture(picture);
		user.setGenreOne(genreOne);
		user.setGenreTwo(genreTwo);
		user.setGenreThree(genreThree);

		userDao.update(user);
		user = userDao.selectById(userId);

		return user;
	}

	@PostMapping("/updateUserPic.chord")
	@ResponseBody
	private void runImportRecordsJob(HttpServletRequest request, HttpServletRequest response) {
		System.out.println("new picture request");
		System.out.println(request.getParameterMap().entrySet());
		System.out.println();
	}

	@GetMapping("/forgetPassword.chord")
	@ResponseBody
	public String forPass(String email) {
		
		User user = userDao.selectByEmail(email);
		
		if(user == null)
			return "{\"status\" : \"bad-email\"}";
		else {
			String tempPassword = PassGen.generateTempPass();
			user.setPassword(Hash.sha256(tempPassword));
			userDao.update(user);
			System.out.println("new password for " + email + ": " + tempPassword);
			Email.sendTempPass(email, tempPassword);
		}
	
		return "{\"status\" : \"ok\"}";
	}
}
