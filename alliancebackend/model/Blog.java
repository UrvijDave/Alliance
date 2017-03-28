package alliancebackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "U_blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int blogid;
	private String blogname;
	// private int userId;
	private String username;
	private String blogdesc;
	private String status; // Will include A,P,R as keyword for Approved,Pending
							// and Rejected respectively
	private String createddate;
	private String blogcomments;
	private int bloglikes;
	private int blogdislikes;

	public int getBlogid() {
		return blogid;
	}

	public void setBlogid(int blogid) {
		this.blogid = blogid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBlogname() {
		return blogname;
	}

	public void setBlogname(String blogname) {
		this.blogname = blogname;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public String toString() {
		return "Blog [blogid=" + blogid + ",createddate=" + createddate + ", blogname=" + blogname + ", userId="
				+ username + ", blogDescription=" + blogdesc + ", status=" + status + "," + ", blogComments="
				+ blogcomments + "]";
	}

	public int getBloglikes() {
		return bloglikes;
	}

	public void setBloglikes(int bloglikes) {
		this.bloglikes = bloglikes;
	}

	public int getBlogdislikes() {
		return blogdislikes;
	}

	public void setBlogdislikes(int blogdislikes) {
		this.blogdislikes = blogdislikes;
	}

	public String getBlogdesc() {
		return blogdesc;
	}

	public void setBlogdesc(String blogdesc) {
		this.blogdesc = blogdesc;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBlogcomments() {
		return blogcomments;
	}

	public void setBlogcomments(String blogcomments) {
		this.blogcomments = blogcomments;
	}
}
