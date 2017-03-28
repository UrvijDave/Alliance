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

import alliancebackend.dao.NewsBulletinDao;
import alliancebackend.model.NewsBulletin;
import alliancebackend.model.User;
import alliancebackend.model.Error;

@RestController
public class NewsBulletinController {

	@Autowired
	NewsBulletinDao nbDao;

	/*-------------------- CREATE NEWS --------------------*/
	
	@RequestMapping(value = "/crenews", method = RequestMethod.POST)
	public ResponseEntity<?> createNewsBulletin(@RequestBody NewsBulletin news, HttpSession session) {
		System.out.println(news.getBulletinBody());
		System.out.println(news.getBulletinDate());
		System.out.println(news.getBulletinHead());

		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}

		//
		else if (user != null && news.getBulletinBody().length() == 0 || news.getBulletinDate().length() == 0
				|| news.getBulletinHead().length() == 0) {
			System.out.println("No data found");
			Error error1 = new Error(2, "Pls. enter valid data..");
			return new ResponseEntity<Error>(error1, HttpStatus.BAD_REQUEST);// 400
		} else {
			nbDao.createNewsBulletin(news);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	/*-------------------- GET ALL NEWS --------------------*/
	
	@RequestMapping(value = "/getAllNews", method = RequestMethod.GET)
	public ResponseEntity<?> getAllNewsBulletin(HttpSession session) {
		List<NewsBulletin> news = nbDao.getAllNewsBulletins();
		return new ResponseEntity<List<NewsBulletin>>(news, HttpStatus.OK);
	}

	/*-------------------- GET ALL NEWS BY HEADLINE --------------------*/
	
	@RequestMapping(value = "/getAllHead", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBulletinHead(HttpSession session) {
		List<NewsBulletin> news = nbDao.getAllBulletinHead();
		return new ResponseEntity<List<NewsBulletin>>(news, HttpStatus.OK);
	}
	
	
	/*-------------------- GET NEWS BY ID --------------------*/

	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public ResponseEntity<NewsBulletin> getNewsBulletinById(@PathVariable(value = "id") int id) {
		NewsBulletin news = nbDao.getNewsBulletinById(id);
		if (news == null) {
			System.out.println("news is null..........................");
			return new ResponseEntity<NewsBulletin>(HttpStatus.NOT_FOUND);
		}
		System.out.println("returning news object..........................");
		return new ResponseEntity<NewsBulletin>(news, HttpStatus.OK);
	}


	/*-------------------- UPDATE NEWS --------------------*/

	@RequestMapping(value = "/news/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateNewsBulletin(@PathVariable int id, @RequestBody NewsBulletin news,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN")) {
			NewsBulletin editnews = nbDao.getNewsBulletinById(id);
			if (editnews == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			NewsBulletin updatedNewsBulletin = nbDao.updateNewsBulletin(id, news);
			return new ResponseEntity<NewsBulletin>(updatedNewsBulletin, HttpStatus.OK);
		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}


	/*-------------------- DELETE NEWS --------------------*/

	@RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNewsBulletin(@PathVariable int id, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Error error = new Error(1, "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		} else if (user.getRole().equalsIgnoreCase("ADMIN"))
		/* else if (user.getRole().equalsIgnoreCase("Admin")) */ {
			NewsBulletin news = nbDao.getNewsBulletinById(id);
			if (news == null)
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			nbDao.deleteNewsBulletin(id);
			return new ResponseEntity<Void>(HttpStatus.OK);

		} else {
			Error error = new Error(2, "Unauthorized user..");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);// 401
		}
	}



}
