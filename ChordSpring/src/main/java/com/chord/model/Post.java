package com.chord.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "POSTS")
public class Post {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;

	@Column(name = "authorId")
	private Integer authorId;

	@Column(name = "description")
	private String description;

	@Column(name = "picture")
	private String picture;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<User> us;

	public Post() {
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Integer getauthorId() {
		return authorId;
	}

	public void setauthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<User> getUs() {
		return us;
	}

	public void setUs(List<User> us) {
		this.us = us;
	}

	@Override
	public String toString() {
		return "Posts [postId=" + postId + ", authorId=" + authorId + ", description=" + description + ", picture="
				+ picture + ", us=" + us + "]";
	}

	public Post(int postId, Integer authorId, String description, String picture, List<User> us) {
		super();
		this.postId = postId;
		this.authorId = authorId;
		this.description = description;
		this.picture = picture;
		this.us = us;
	}
	public Post(Integer authorId, String description, String picture, List<User> us) {
		super();
		this.authorId = authorId;
		this.description = description;
		this.picture = picture;
		this.us = us;
	}
}
