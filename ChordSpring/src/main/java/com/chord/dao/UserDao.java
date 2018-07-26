package com.chord.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chord.model.User;

/**
 * This class holds methods to access, retrieve, and change data pertaining to
 * Users in the database
 * 
 * @author Ezzdean Bietar
 *
 */
@Repository("userDao")
@Transactional
public class UserDao {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private SessionFactory sessionFactory;

	public UserDao() {

	}

	/**
	 * Inserts a new User in the database.
	 * 
	 * @param user - the new User
	 */
	public void insert(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	/**
	 * Updates an excisting User in the database.
	 * 
	 * @param user - the User to be updated
	 */
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	/**
	 * Searches the database for a User matching the id parameter.
	 * If the User with the specified id does not exist, null will be returned.
	 * 
	 * @param id - the id of the User
	 * @return the User with the specified id, or null if the user does not exist.
	 */
	public User selectById(int id) {
		System.out.println("Retrieveing user with id: " + id);
		User user = sessionFactory.getCurrentSession().get(User.class, id);
		System.out.println(user);
		return user;
	}

	/**
	 * Searches the database for a User matching the email parameter.
	 * If the User with the specified email does not exist, null will be returned.
	 * 
	 * @param email
	 * @return the User with the specified email, or null if the user does not exist.
	 */
	public User selectByEmail(String email) {

		User user = sessionFactory.getCurrentSession().createQuery("from User where email = '" + email + "'", User.class).uniqueResult();
		System.out.println(user);
		return user;
	}
	
	/**
	 * Returns a list of Users that have the name parameter in their firstnames.
	 * 
	 * @param name - the name to be searched
	 * @return The list of Users with firstnames that include the name parameter
	 */
	public List<User> searchByName(String name) {
		
		name = name.toLowerCase();
		List<User> users = sessionFactory.getCurrentSession().createQuery("from User where lower(firstname) like '%" + name + "%'", User.class).list();
		System.out.println("serching for name: " + name);
		System.out.println(users);
		return users;
	}
	
	/**
	 * Returns the list of all Users in the database
	 * 
	 * @return list of all the Users in the database
	 */
	public List<User> selectAll() {
		return sessionFactory.getCurrentSession().createQuery("from Users", User.class).list();
	}
}
