package alliancebackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "U_job")
public class Job {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int jobid;
		private String title;
		private String description;
		private String postdate;
		private String qualification;
		private int salary;
		private String location;
		private int experience;
		private String companyname;
		private String status; // V-Vacanat, F-Filled, P-Pending ,A = Approved,R=
								// rejected
		private String Expirydate;

		public int getJobid() {
			return jobid;
		}

		public void setJobid(int jobid) {
			this.jobid = jobid;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getQualification() {
			return qualification;
		}

		public void setQualification(String qualification) {
			this.qualification = qualification;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getCompanyname() {
			return companyname;
		}

		public void setCompanyname(String companyname) {
			this.companyname = companyname;
		}

		public int getSalary() {
			return salary;
		}

		public void setSalary(int salary) {
			this.salary = salary;
		}

		public int getExperience() {
			return experience;
		}

		public void setExperience(int experience) {
			this.experience = experience;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getPostdate() {
			return postdate;
		}

		public void setPostdate(String postdate) {
			this.postdate = postdate;
		}

		public String getExpirydate() {
			return Expirydate;
		}

		public void setExpirydate(String expirydate) {
			Expirydate = expirydate;
		}
	}

