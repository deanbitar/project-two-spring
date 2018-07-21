package com.chord.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chord.model.User;

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

	public void insert(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	public User selectById(int id) {
		System.out.println("Retrieveing user with id: " + id);
		User user = sessionFactory.getCurrentSession().get(User.class, id);
		System.out.println(user);
		return user;
	}

	public User selectByEmail(String email) {

		User user = sessionFactory.getCurrentSession().createQuery("from Users where email = " + email, User.class)
				.uniqueResult();

		return user;
	}

	public List<User> selectAll() {
		return sessionFactory.getCurrentSession().createQuery("from Users", User.class).list();
	}
}
