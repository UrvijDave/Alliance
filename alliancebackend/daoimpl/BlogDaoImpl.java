package alliancebackend.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import alliancebackend.dao.BlogDao;
import alliancebackend.model.Blog;

@Repository
public class BlogDaoImpl implements BlogDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void createBlog(Blog blog) {

		Session session = sessionFactory.openSession();
		session.save(blog);
		session.flush();
		session.close();
	}

	public List<Blog> getAllBlogs(String role) {

		Session session = sessionFactory.openSession();
		Query query;
		if (role.equals("ADMIN"))
			query = session.createQuery("from Blog order by blogid desc");
		else
			query = session.createQuery("from Blog where status = 'A' order by blogid desc");

		List<Blog> blogs = query.list();
		return blogs;

	}

	public Blog updateBlog(int blogid, Blog blog) {

		Session session = sessionFactory.openSession();
		System.out.println("Id of Blog to update is: " + blog.getBlogid());
		if (session.get(Blog.class, blogid) == null)
			return null;

		session.merge(blog);
		Blog updateBlog = (Blog) session.get(Blog.class, blogid);

		session.flush();
		session.close();
		return updateBlog;

	}

	public Blog getBlogById(int id) {

		Session session = sessionFactory.openSession();

		Query qry = session.createQuery("from Blog where blogid=" + id);
		Blog blog = (Blog) qry.uniqueResult();
		System.out.println("........................." + blog.getBlogid());
		System.out.println("........................." + blog.getBlogname());
		System.out.println("........................." + blog.getBlogdesc());

		return blog;

	}

	public void deleteBlog(int blogid) {
		Session session = sessionFactory.openSession();

		Blog blog = (Blog) session.get(Blog.class, blogid);
		session.delete(blog);
		session.flush();
		session.close();
	}

	public List<Blog> getBlogByStatus(String status) {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Blog where status ='" + status + "' order by blogid description");
		List<Blog> blogs = query.list();
		session.close();
		return blogs;
	}

	@Transactional
	public Blog bloglikes(int blogid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("update Blog set bloglikes = bloglikes+1 where blogid = " + blogid);
		query.executeUpdate();
		Blog updateBlog = (Blog) session.get(Blog.class, blogid);
		session.flush();
		session.close();
		return updateBlog;

	}

	@Transactional
	public Blog blogdislikes(int blogid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("update Blog set blogdislikes = blogdislikes+1 where blogid = " + blogid);
		query.executeUpdate();
		Blog updateBlog = (Blog) session.get(Blog.class, blogid);
		session.flush();
		session.close();
		return updateBlog;
	}

	/*
	 * public List<Blog> getAllBlogName() {
	 * 
	 * Session session = sessionFactory.openSession(); Query query = session
	 * .createQuery("select blogid blogname from blog where ststus='A' order by blogid description"
	 * );
	 * 
	 * List<Blog> blogs = query.list(); session.close(); return blogs;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 */

}
