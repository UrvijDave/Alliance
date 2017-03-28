package alliancebackend.dao;

import java.util.List;

import alliancebackend.model.Event;
public interface EventDao {

	public void postEvent(Event event);	
	
	public List<Event> getAllEvents();

	public Event getEventById(int eventid);
	
	public List<Event> getAllEventName();

	public Event updateEvent(int eventid, Event event);

	public void deleteEvent(int eventid);
}
