package alliancebackend.dao;

import java.util.List;

import alliancebackend.model.Blog;

public interface BlogDao {

	public void createBlog(Blog blog);

	public List<Blog> getAllBlogs(String role);

	public Blog updateBlog(int blogid, Blog blog);

	public Blog getBlogById(int blogid);
	
	public void deleteBlog(int blogid);
	
	public Blog bloglikes(int blogid);
	
	public Blog blogdislikes(int blogid);
	
	 /* public List<Blog> getAllBlogName();
	  public List<Blog> getBlogByStatus(String status); 
	 */
}
