package alliancebackend.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import alliancebackend.dao.BlogDao;
import alliancebackend.model.Blog;
import alliancebackend.model.User;
import alliancebackend.model.Error;
import alliancebackend.model.Job;

@RestController
public class BlogController {

	@Autowired
	private BlogDao blogDao;

					/*---------- CREATE BLOG ----------*/

	@RequestMapping(value = "/creblog", method = RequestMethod.POST)
	public ResponseEntity<?> createBlog(@RequestBody Blog blog, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user... login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		} else if (user != null && (blog.getBlogname().length() == 0 || blog.getBlogdesc().length() == 0)) {
			System.out.println("No data found");
			Error error1 = new Error(2, "Please enter valid data...");
			return new ResponseEntity<Error>(error1, HttpStatus.BAD_REQUEST);

		} else {
			blog.setUsername(user.getUsername());
			blog.setStatus("P");
			blog.setBlogdislikes(0);
			blog.setBloglikes(0);
			blog.setBlogcomments(null);

			DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			Date date = new Date();
			blog.setCreateddate(dateFormat.format(date));

			blogDao.createBlog(blog);
			return new ResponseEntity<Void>(HttpStatus.OK);

		}

	}
	
	/*-------------------- GET BLOG BY ID --------------------*/
	@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> getBlogById(@PathVariable(value = "id") int id) {
		Blog blog = blogDao.getBlogById(id);
		if (blog == null) {
			System.out.println("blog is null..........................");
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		System.out.println("returning blog object..........................");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

				/*----------GET ALL BLOG ----------*/

	@RequestMapping(value = "/getAllBlogs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogs(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			List<Blog> blogs = blogDao.getAllBlogs("GUEST");
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			List<Blog> blogs = blogDao.getAllBlogs("ADMIN");
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		} else {
			List<Blog> blogs = blogDao.getAllBlogs(user.getRole());
			return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
		}
	}

					/*----------UPDATE BLOG----------*/

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody Blog blog, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			Blog editblog = blogDao.getBlogById(id);
			if (editblog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			Blog updatedBlog = blogDao.updateBlog(id, blog);
			return new ResponseEntity<Blog>(updatedBlog, HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

						/* ----------DELETE BLOG---------- */

	@RequestMapping(value = "/blog/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBlog(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN"))
		/* else if (user.getRole().equalsIgnoreCase("Admin")) */ {
			Blog blog = blogDao.getBlogById(id);
			if (blog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			blogDao.deleteBlog(id);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}

					/* ---------- BLOG LIKES---------- */

	@RequestMapping(value = "/bloglike/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBlogLikes(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else {
			System.out.println("blog likes in backend controller");
			Blog editblog = blogDao.getBlogById(id);
			if (editblog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			Blog updatedBlog = blogDao.bloglikes(id);
			return new ResponseEntity<Blog>(updatedBlog, HttpStatus.OK);
		}
	}

					/* ---------- BLOG DISLIKES---------- */

	@RequestMapping(value = "/blogdislike/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBlogdisLikes(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else {
			Blog editblog = blogDao.getBlogById(id);
			if (editblog == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		System.out.println("blog dislikes in backend controller");
		Blog updatedBlog = blogDao.blogdislikes(id);
		return new ResponseEntity<Blog>(updatedBlog, HttpStatus.OK);
	}

	/*----------GET ALL BLOG NAME----------*/

	/*
	 * @RequestMapping(value = "/getAllBlogName", method = RequestMethod.GET)
	 * public ResponseEntity<?> getAllBlogname(HttpSession session) { List<Blog>
	 * blogs = blogDao.getAllBlogName(); return new
	 * ResponseEntity<List<Blog>>(blogs, HttpStatus.OK); }
	 * 
	 * 
	 * @RequestMapping(value = "/blog/{id}", method = RequestMethod.GET) public
	 * ResponseEntity<Blog> getBlogById(@PathVariable(value = "id") int id) {
	 * Blog blog = blogDao.getBlogById(id); if (blog == null) {
	 * System.out.println("blog is null.........................."); return new
	 * ResponseEntity<Blog>(HttpStatus.NOT_FOUND); }
	 * System.out.println("returning blog object..........................");
	 * return new ResponseEntity<Blog>(blog, HttpStatus.OK); }
	 * 
	 * 
	 * 
	 		/*----------GET BLOG BY STATUS ----------
	
	  @RequestMapping(value = "/blogsta/{status}", method = RequestMethod.GET)
	  public ResponseEntity<?> getBlogByStatus(@PathVariable(value = "status")
	  String status, HttpSession session) { User user = (User)
	  session.getAttribute("user"); if (user == null) { Error error = new
	  Error(1, "Unauthorized user.. login using valid credentials"); return new
	  ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401 } else if
	  (user.getRole().equalsIgnoreCase("ADMIN")) { List<Blog> blogs =
	  blogDao.getBlogByStatus(status); return new
	  ResponseEntity<List<Blog>>(blogs, HttpStatus.OK); } else { Error error =
	  new Error(2, "Unauthorized user.."); return new
	  ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401 } 
	  }
	*/
	  
}
