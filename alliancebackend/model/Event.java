package alliancebackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "U_event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int EventId;
	// private String EventPhoto;
	private String EventName;
	private String EventContent;
	private String EventVenue;
	private String EventDate;
	private String EventTime;

	public String getEventName() {
		return EventName;
	}

	public void setEventName(String eventName) {
		EventName = eventName;
	}

	public String getEventContent() {
		return EventContent;
	}

	public void setEventContent(String eventContent) {
		EventContent = eventContent;
	}

	public String getEventVenue() {
		return EventVenue;
	}

	public void setEventVenue(String eventVenue) {
		EventVenue = eventVenue;
	}

	public int getEventId() {
		return EventId;
	}

	public void setEventId(int eventId) {
		EventId = eventId;
	}

	public String getEventDate() {
		return EventDate;
	}

	public void setEventDate(String eventDate) {
		EventDate = eventDate;
	}

	public String getEventTime() {
		return EventTime;
	}

	public void setEventTime(String eventTime) {
		EventTime = eventTime;
	}

}
