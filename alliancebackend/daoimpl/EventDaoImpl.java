package alliancebackend.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import alliancebackend.dao.EventDao;
import alliancebackend.model.Event;

@Repository
public class EventDaoImpl implements EventDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*-------------------- POST EVENT --------------------*/
	public void postEvent(Event event) {
		Session session = sessionFactory.openSession();
		session.save(event);
		session.flush();
		session.close();
	}

	/*-------------------- GET ALL EVENTS --------------------*/
	public List<Event> getAllEvents() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Event order by EventId desc");
		List<Event> events = query.list();
		session.close();
		return events;
	}

	/*-------------------- GET EVENT BY ID --------------------*/
	public Event getEventById(int id) {
		Session session = sessionFactory.openSession();
		// select * from personinfo where id=2
		Event event = (Event) session.get(Event.class, id);
		session.close();
		return event;

	}

	/*-------------------- GET ALL EVENTNAME --------------------*/
	public List<Event> getAllEventName() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select EventName from  Event order by EventId desc");
		List<Event> events = query.list();
		session.close();
		return events;
	}

	/*-------------------- UPDATE EVENTS --------------------*/

	public Event updateEvent(int eventid, Event event) {
		Session session = sessionFactory.openSession();
		System.out.println("Id of Event is to update is: " + event.getEventId());
		if (session.get(Event.class, eventid) == null)
			return null;
		session.merge(event); // update query where personid=?
		// select [after modification]
		Event updatedEvent = (Event) session.get(Event.class, eventid); // select
																		// query
		session.flush();
		session.close();
		return updatedEvent;

	}

	/*-------------------- DELETE EVENT --------------------*/
	public void deleteEvent(int eventid) {
		Session session = sessionFactory.openSession();

		Event event = (Event) session.get(Event.class, eventid);
		session.delete(event);

		session.flush();
		session.close();
	}

}
