package alliancebackend.dao;

import java.util.List;

import alliancebackend.model.NewsBulletin;


public interface NewsBulletinDao {

	public void createNewsBulletin(NewsBulletin news);

	public List<NewsBulletin> getAllNewsBulletins();

	public List<NewsBulletin> getAllBulletinHead();

	public NewsBulletin getNewsBulletinById(int bulletinid);

	public NewsBulletin updateNewsBulletin(int bulletinid, NewsBulletin news);

	public void deleteNewsBulletin(int bulletinid);

}
