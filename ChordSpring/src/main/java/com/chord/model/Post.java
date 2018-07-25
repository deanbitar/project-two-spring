package com.chord.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Posts")
public class Post {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "picture")
	private String picture;

	@Column(name = "submit_time", nullable = false)
	private Date submitTime;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "LIKED_POSTS", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	Set<User> likedUsers;

	public Post() {

	}

	public Post(User author, String description, String picture) {
		super();
		this.author = author;
		this.description = description;
		this.picture = picture;
	}

	public Post(int postId, User author, String description, String picture) {
		super();
		this.postId = postId;
		this.author = author;
		this.description = description;
		this.picture = picture;
	}

	public Post(int postId, User author, String description, String picture, Date submitTime) {
		super();
		this.postId = postId;
		this.author = author;
		this.description = description;
		this.picture = picture;
		this.submitTime = submitTime;
	}

	public Post(int postId, User author, String description, String picture, Date submitTime, Set<User> usersLiked) {
		super();
		this.postId = postId;
		this.author = author;
		this.description = description;
		this.picture = picture;
		this.submitTime = submitTime;
		this.likedUsers = usersLiked;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Set<User> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(Set<User> likedUsers) {
		this.likedUsers = likedUsers;
	}

	@Override
	public boolean equals(Object obj) {

		if (!obj.getClass().equals(this.getClass()))
			return false;
		else
			return this.getPostId() == ((Post) obj).getPostId();
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", description=" + description + ", picture=" + picture + ", submitTime="
				+ submitTime + "likedUsers=" + likedUsers + "]";
	}
}
