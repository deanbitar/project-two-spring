package com.chord.controller;

import java.util.List;
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

/**
 * This class is a controller class used to respond to HTTP methods and
 * perform actions against User objects
 * 
 * @author Ezzdean Bietar
 *
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200")
@MultipartConfig
public class UserController {

	@Autowired
	private UserDao userDao;

	public UserController() {

	}

	/**
	 * Creates a new User with parameters passed in from the HTTP request.
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param dob
	 * @param password
	 * @param genreOne
	 * @param genreTwo
	 * @param genreThree
	 * @return a JOSN with status of either 'ok' or 'email taken'
	 */
	@GetMapping(value = "/createUser.chord")
	@ResponseBody
	public String createNewUser(String firstname, String lastname, String email, String dob,
			String password, String genreOne, String genreTwo, String genreThree) {

		System.out.println("Create request with firstname: " + firstname + " last name " + lastname + " email " + email);
		
		if(userDao.selectByEmail(email) == null) {
			User user = new User();

			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setEmail(email);
			user.setDob(dob);
			user.setPassword(Hash.sha256(password));
			user.setPicture("http://apagnikulakshatriya.com/images/male.jpg");
			user.setGenreOne(genreOne);
			user.setGenreTwo(genreTwo);
			user.setGenreThree(genreThree);

			userDao.insert(user);
			
			System.out.println("Adding user: " + user);
			return "{\"status\" : \"ok\"}";
		}
		else {
			return "{\"status\" : \"email taken\"}";
		}
	}

	/**
	 * Returns the User object with the id parameter in JSON.
	 * 
	 * @param userId
	 * @return the JSON of the user, or null if the user doesn't exist
	 */
	@GetMapping(value = "/getUser.chord")
	public @ResponseBody User getUser(int userId) {
		System.out.println("getting user: " + userId);
		return userDao.selectById(userId);
	}

	/**
	 * 
	 * 
	 * @param userId
	 * @param friendId
	 * @return
	 *//*
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
	}*/

	/**
	 * Checks the credentials of a User and returns the JSON if the email
	 * and password are a match.
	 * 
	 * @param request
	 * @param response
	 * @return User JSON if the credentials match, or null otherwise
	 */
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

	/**
	 * Returns a list of Users that have the name parameter in their firstnames.
	 * The list is returned 
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/searchUserByName.chord")
	public @ResponseBody List<User> searchUsers(String name) {
		return userDao.searchByName(name);
	}

	/**
	 * Updates the properties of the User with the specified id parameter.
	 * 
	 * @param userId
	 * @param email
	 * @param dob
	 * @param bio
	 * @param picture
	 * @param genreOne
	 * @param genreTwo
	 * @param genreThree
	 * @return the User with the updated data in JSON.
	 */
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

	/**
	 * Updates the profile picture of the User with the id parameter.
	 * 
	 * @param request
	 * @param response
	 */
	@PostMapping("/updateUserPic.chord")
	@ResponseBody
	private void runImportRecordsJob(HttpServletRequest request, HttpServletRequest response) {
		System.out.println("new picture request");
		System.out.println(request.getParameterMap().entrySet());
		System.out.println();
	}

	/**
	 * Assigns a randomly generated String as the new password of the User
	 * with the matching email parameter. An email with the temporary password
	 * will also be sent the email address.
	 * 
	 * @param email
	 * @return the status in JSON of either 'ok' or 'bad-email'
	 */
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
	
	/**
	 * Updates the password of the User with the specified id parameter to
	 * the new password parameter
	 * 
	 * @param userId
	 * @param newPassword
	 * @return status 'ok' in JSON
	 */
	@GetMapping("/changePassword.chord")
	@ResponseBody
	public String changeUserPass(int userId, String newPassword) {
		
		User user = userDao.selectById(userId);
		user.setPassword(Hash.sha256(newPassword));
		userDao.update(user);
		
		return "{\"status\" : \"ok\"}";
	}
}
