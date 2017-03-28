package alliancebackend.controller;

import alliancebackend.dao.UserDao;
import alliancebackend.model.User;
import alliancebackend.model.Error;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@RequestMapping("/")
	public ModelAndView doindex() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") int id) {
		User user = userDao.getUserById(id);

		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println(userDao.getAllUsers());
		List<User> users = userDao.getAllUsers();

		if (users.isEmpty())
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	/*---------- LOGIN ----------*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)

	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		logger.debug("Entering USERCONTROLLER : LOGIN");
		User validUser = userDao.authenticate(user);

		if (validUser == null) {
			logger.debug("validUser is null");
			Error error = new Error(1, "Username and password doesnt exists...");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED); // ERROR 401
		} else {
			validUser.setOnline(true);
			session.setAttribute("user", userDao.updateUser(validUser.getUserId(), validUser));
			logger.debug("validUser is not Null");
			return new ResponseEntity<User>(validUser, HttpStatus.OK);// Error 200
		}
	}

	/*---------- LOGOUT ----------*/
	@RequestMapping(value = "/logout", method = RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			user.setOnline(false);
			userDao.updateUser(user.getUserId(), user);
		}
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*---------- REGISTER ----------*/

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user)

	{
		try {
			logger.debug("USERCONTROLLER=>REGISTER " + user);

			user.setOnline(false);
			User savedUser = userDao.registerUser(user);
			logger.debug("User Id generated is " + savedUser.getUserId());

			if (savedUser.getUserId() == 0) {
				Error error = new Error(2, "Couldnt insert user details ");
				return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
			} else
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			Error error = new Error(2,
					"Could not inster User Details. Cannot have null/duplicate values " + e.getMessage());
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*---------- EDIT USERS----------*/

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {

		User updatedUser = userDao.updateUser(id, user);
		if (user == null)
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);

	}

	/*---------- DELETE USERS----------*/

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		User user = userDao.getUserById(id);
		if (user == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		userDao.deleteUser(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*---------- GET USERS----------*/

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ResponseEntity<Error>(new Error(1, "Unauthorized user"), HttpStatus.UNAUTHORIZED);
		else {
			List<User> users = userDao.getAllUsers(user);
			for (User u : users)
				System.out.println("IsONline " + u.isOnline());
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}

	}
}