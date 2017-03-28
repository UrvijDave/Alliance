package alliancebackend.dao;

import java.util.List;
import alliancebackend.model.Job;

public interface JobDao {

	public void postJob(Job job);

	public Job getJobById(int jobid);

	public List<Job> getAllJobs();

	public List<Job> getAllTitle();

	public Job updateJob(int jobid, Job job);

	public void deleteJob(int jobid);

	public List<Job> getJobByStatus();

	public List<Job> getJobByExpirydate(String expirydate);

}
