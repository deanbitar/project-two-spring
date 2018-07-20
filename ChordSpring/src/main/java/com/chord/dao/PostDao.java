package com.chord.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chord.model.Post;

@Repository("postDao")
@Transactional
public class PostDao {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public PostDao() {
		
	}
	
	public void insert(Post post) {
		sessionFactory.getCurrentSession().save(post);
	}
	
	public void update(Post post) {
		sessionFactory.getCurrentSession().update(post);
	}
	
	public Post selectById(int id) {
		return sessionFactory.getCurrentSession().get(Post.class, id);
	}
	
	public List<Post> selectAll() {
		return sessionFactory.getCurrentSession().createQuery("from Posts", Post.class).list();
	}
}
