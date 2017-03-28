
package alliancebackend.controller;

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

import alliancebackend.dao.EventDao;
import alliancebackend.model.User;
import alliancebackend.model.Error;
import alliancebackend.model.Event;

@RestController
public class EventController {

	@Autowired
	private EventDao eventDao;

	/*-------------------- POST EVENT --------------------*/

	@RequestMapping(value = "/postEvent", method = RequestMethod.POST)
	public ResponseEntity<?> postEvent(@RequestBody Event event, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user != null && event.getEventName().length() == 0 || event.getEventContent().length() == 0
				|| event.getEventDate().length() == 0 || event.getEventVenue().length() == 0
				|| event.getEventTime().length() == 0) {
			System.out.println("No data found");
			Error error1 = new Error(2, "Pls. enter valid data..");
			return new ResponseEntity<Error>(error1, HttpStatus.BAD_REQUEST);// 400
		}

		else {
			eventDao.postEvent(event);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	/*-------------------- GET ALL EVENTS --------------------*/
	@RequestMapping(value = "/getAllEvents", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEvents(HttpSession session) {
		List<Event> events = eventDao.getAllEvents();
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}

	/*-------------------- GET ALL EVENT NAME --------------------*/
	@RequestMapping(value = "/getAllEventName", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEventName(HttpSession session) {
		List<Event> events = eventDao.getAllEventName();
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}
	
	/*-------------------- UPDATE EVENT --------------------*/

	@RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody Event event, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			Event editevent = eventDao.getEventById(id);
			if (editevent == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			Event updatedEvent = eventDao.updateEvent(id, event);
			return new ResponseEntity<Event>(updatedEvent, HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}



	/*-------------------- GET EVENT BY ID --------------------*/
	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
	public ResponseEntity<Event> getEventById(@PathVariable(value = "id") int id) {
		Event event = eventDao.getEventById(id);
		if (event == null) {
			System.out.println("event is null..........................");
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
		}
		System.out.println("returning event object..........................");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	/*-------------------- DELETE EVENT --------------------*/
	@RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEvent(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN"))
		/* else if (user.getRole().equalsIgnoreCase("Admin")) */ {
			Event event = eventDao.getEventById(id);
			if (event == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			eventDao.deleteEvent(id);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}
}
