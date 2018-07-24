package com.chord.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Posts")
public class Post {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;

	@Column(name = "description", nullable=false)
	private String description;

	@Column(name = "picture")
	private String picture;
	
	@Column(name="submit_time", nullable=false)
	private Date submitTime;

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
	
	@Override
	public boolean equals(Object obj) {
		
		if(!obj.getClass().equals(this.getClass()))
			return false;
		else
			return this.getPostId() == ((Post)obj).getPostId();
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", description=" + description + ", picture=" + picture
				+ ", submitTime=" + submitTime + "]";
	}
}
