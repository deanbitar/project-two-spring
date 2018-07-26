package com.chord.dao;

import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chord.model.Post;

/**
 * This class holds methods to access, retrieve, and change data pertaining to
 * Posts in the database
 * 
 * @author Ezzdean Bietar
 *
 */
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
	
	/**
	 * Inserts a Post into the database
	 * 
	 * @param post - the new Post
	 */
	public void insert(Post post) {
		sessionFactory.getCurrentSession().save(post);
	}
	
	/**
	 * Updates the parameter Post in the database
	 * 
	 * @param post - the Post to be updated
	 */
	public void update(Post post) {
		sessionFactory.getCurrentSession().update(post);
	}
	
	/**
	 * Searches the database for a Post matching the id parameter.
	 * If the Post with the specified id does not exist, null will be returned.
	 * 
	 * @param id - the id of the Post
	 * @return the Post with the specified id, or null if the post does not exist.
	 */
	public Post selectById(int id) {
		return sessionFactory.getCurrentSession().get(Post.class, id);
	}
	
	/**
	 * Returns the list of all Posts in the database
	 * 
	 * @return the list of all Posts
	 */
	public List<Post> selectAll() {
		return sessionFactory.getCurrentSession().createQuery("from Post", Post.class).list();
	}
}
