package alliancebackend.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import alliancebackend.dao.NewsBulletinDao;
import alliancebackend.model.NewsBulletin;

@Repository
public class NewsBulletinDaoImpl implements NewsBulletinDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/*-------------------CREATE NEWS -------------------*/
	public void createNewsBulletin(NewsBulletin news) {
		Session session = sessionFactory.openSession();
		session.save(news);
		session.flush();
		session.close();
	}

	/*-------------------GET ALL NEWS -------------------*/
	public List<NewsBulletin> getAllNewsBulletins() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from NewsBulletin  order by bulletinid desc");
		List<NewsBulletin> nb = query.list();
		session.close();
		return nb;
	}

	/*-------------------GET ALL NEWS BY HEADLINES -------------------*/
	public List<NewsBulletin> getAllBulletinHead() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(" select bulletinHead from NewsBulletin  order by bulletinid desc");
		List<NewsBulletin> nb = query.list();
		session.close();
		return nb;
	}

	/*-------------------GET ALL NEWS BY ID -------------------*/
	public NewsBulletin getNewsBulletinById(int id) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		NewsBulletin nb = (NewsBulletin) session.get(NewsBulletin.class, id);
		session.close();
		return nb;

	}

	/*-------------------UPDATE NEWS -------------------*/
	public NewsBulletin updateNewsBulletin(int bulletinid, NewsBulletin news) {
		Session session = sessionFactory.openSession();
		System.out.println("Id of NewsBulletin is to update is: " + news.getBulletinId());
		if (session.get(NewsBulletin.class, bulletinid) == null)
			return null;
		session.merge(news); //

		NewsBulletin updatedNewsBulletin = (NewsBulletin) session.get(NewsBulletin.class, bulletinid); // select
		// query
		session.flush();
		session.close();
		return updatedNewsBulletin;

	}

	/*-------------------DELETE NEWS -------------------*/
	public void deleteNewsBulletin(int bulletinid) {
		Session session = sessionFactory.openSession();

		NewsBulletin nb = (NewsBulletin) session.get(NewsBulletin.class, bulletinid);
		session.delete(nb);

		session.flush();
		session.close();

	}
	
	
	
}
