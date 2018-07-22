package com.chord.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "dob", nullable = false)
	private String dob;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "genre_one", nullable = false)
	private String genreOne;

	@Column(name = "genre_two")
	private String genreTwo;

	@Column(name = "genre_three")
	private String genreThree;

	@Column(name = "picture")
	private String picture;

	@Column(name = "bio")
	private String bio;

	@JsonIgnore
	@OneToMany(mappedBy="author", fetch=FetchType.EAGER)
	private Set<Post> posts;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "table_friends", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
	private Set<User> friends;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "table_friends", joinColumns = @JoinColumn(name = "friendId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<User> friendsOf;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	 * 
	 * @JoinColumn(name="us_id") private User relation;
	 */

	public User() {
	}
	
	public User(String firstname, String lastname, String email, String dob, String password,
			String genreone, String genretwo, String genrethree, String picture, String bio) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.dob = dob;
		this.password = password;
		this.genreOne = genreone;
		this.genreTwo = genretwo;
		this.genreThree = genrethree;
		this.picture = picture;
		this.bio = bio;
	}
	
	public User(String firstname, String lastname, String email, String dob, String password,
			String genreone, String genretwo, String genrethree, String picture, String bio, Set<Post> posts,
			Set<User> friends, Set<User> friendsOf) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.dob = dob;
		this.password = password;
		this.genreOne = genreone;
		this.genreTwo = genretwo;
		this.genreThree = genrethree;
		this.picture = picture;
		this.bio = bio;
		this.posts = posts;
		this.friends = friends;
		this.friendsOf = friendsOf;
	}

	public User(int userId, String firstname, String lastname, String email, String dob, String password,
			String genreone, String genretwo, String genrethree, String picture, String bio, Set<Post> posts,
			Set<User> friends, Set<User> friendsOf) {
		super();
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.dob = dob;
		this.password = password;
		this.genreOne = genreone;
		this.genreTwo = genretwo;
		this.genreThree = genrethree;
		this.picture = picture;
		this.bio = bio;
		this.posts = posts;
		this.friends = friends;
		this.friendsOf = friendsOf;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGenreOne() {
		return genreOne;
	}

	public void setGenreOne(String genreone) {
		this.genreOne = genreone;
	}

	public String getGenreTwo() {
		return genreTwo;
	}

	public void setGenreTwo(String genretwo) {
		this.genreTwo = genretwo;
	}

	public String getGenreThree() {
		return genreThree;
	}

	public void setGenreThree(String genrethree) {
		this.genreThree = genrethree;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<User> getFriendsOf() {
		return friendsOf;
	}

	public void setFriendsOf(Set<User> friendsOf) {
		this.friendsOf = friendsOf;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!obj.getClass().equals(this.getClass()))
			return false;
		else
			return this.userId == ((User) obj).getUserId();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", dob=" + dob + ", password=" + password + ", genreone=" + genreOne + ", genretwo=" + genreTwo
				+ ", genrethree=" + genreThree + ", picture=" + picture + ", bio=" + bio + "]";
	}
}
