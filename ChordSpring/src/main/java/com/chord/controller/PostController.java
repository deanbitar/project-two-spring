package com.chord.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chord.dao.PostDao;
import com.chord.dao.UserDao;
import com.chord.model.Post;
import com.chord.model.User;

/**
 * This class is a controller class used to respond to HTTP methods and
 * perform actions against Post objects
 * 
 * @author Ezzdean Bietar
 *
 */
@Controller
@CrossOrigin(origins="http://localhost:4200")
public class PostController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PostDao postDao;

	/**
	 * Creates a new Post with parameters passed in from the HTTP request.
	 * 
	 * @param userId
	 * @param message
	 * @param picture
	 * @return the JSON representation of the new Post object
	 */
	@GetMapping(value="/createPost.chord")
	public @ResponseBody Post insert(int userId, String message, String picture) {
		
		System.out.println("post insert called");
		User user = userDao.selectById(userId);
		System.out.println("user retrieved");
		System.out.println(user);
		
		Post post = new Post();
		post.setAuthor(user);
		post.setDescription(message);
		post.setPicture(picture);
		post.setSubmitTime(new Date());
		
		System.out.println("inserting post: " + post);
		
		postDao.insert(post);
		
		System.out.println("inserted post: " + post);
		
		return post;
	}
	
	/**
	 * Returns a set of all the Posts that belong to the User with
	 * the id parameter
	 * 
	 * @param userId
	 * @return the set of the Posts of the User
	 */
	@GetMapping("/getUserPosts.chord")
	public @ResponseBody Set<Post> getUserPosts(int userId) {
		
		return userDao.selectById(userId).getPosts();
	}
	
	/**
	 * Returns a list of all Posts in the database.
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/getUserFeed.chord")
	public @ResponseBody List<Post> getUserFeed(int userId) {
		
		return postDao.selectAll();
	}
	
	/**
	 * Add the User with userId parameter to the list of likedUsers of the Post
	 * with the postId parameter
	 * 
	 * @param userId
	 * @param postId
	 * @return the post in JSOm with the updated list of liked Users
	 */
	@GetMapping("/likePost.chord")
	public @ResponseBody Post likePost(int userId, int postId) {
		
		System.out.println("new like request");
		
		User user = userDao.selectById(userId);
		Post post = postDao.selectById(postId);
		
		post.getLikedUsers().add(user);
		postDao.update(post);
		
		return postDao.selectById(postId);
	}
}
