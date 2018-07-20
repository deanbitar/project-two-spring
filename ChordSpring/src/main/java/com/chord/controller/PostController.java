package com.chord.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chord.dao.PostDao;
import com.chord.dao.UserDao;
import com.chord.model.Post;
import com.chord.model.User;

@Controller
public class PostController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PostDao postDao;

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
}
