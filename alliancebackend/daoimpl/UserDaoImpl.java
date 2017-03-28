package alliancebackend.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import alliancebackend.dao.UserDao;
import alliancebackend.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {

		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User authenticate(User user) {

		logger.debug("USERDAOIMPL :: AUTHENTICATE");
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User where username=? and password=?");
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser = (User) query.uniqueResult();
		session.flush();
		session.close();

		if (validUser != null) {
			logger.debug("VALID USER IS " + validUser.getUsername() + " " + validUser.getRole() + " "
					+ validUser.isOnline());
		}
		if (validUser == null)
			logger.debug("Valid USER is null");
		return validUser;
	}

	/*----------UPDATE USER----------*/

	@Transactional
	public User updateUser(int id, User user) {
		Session session = sessionFactory.openSession();

		System.out.println("Id of User is to update is: " + user.getUserId());
		if (session.get(User.class, id) == null)
			return null;
		session.merge(user);
		User updateUser = (User) session.get(User.class, id);

		session.flush();
		session.close();
		return updateUser;
	}

	/*----------DELETE USER----------*/

	public void deleteUser(int id) {

		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, id);
		session.delete(user);
		session.flush();
		session.close();
	}

	/*----------REGISTER USER----------*/

	public User registerUser(User user) {

		logger.debug("USERDAOIMPL - registerUser");
		Session session = sessionFactory.openSession();
		session.save(user);
		session.flush();
		session.close();
		logger.debug("User id in Dao " + user.getUserId());
		return user;
	}

	/*----------LIST USER----------*/

	public List<User> getAllUsers() {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		session.close();
		return users;
	}

	/*----------GET USER BY ID ----------*/

	public User getUserById(int userid) {

		Session session = sessionFactory.openSession();
		User user = (User) session.get(User.class, userid);
		session.close();
		return user;
	}

	/*----------GET ALL USERS ----------*/

	public List<User> getAllUsers(User user) {

		Session session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery(
				"select * from U_users where username in (select username from U_users where username!=? minus(select to_id from U_friend where from_id=? union select from_id from U_friend where to_id=?))");
		query.setString(0, user.getUsername());
		query.setString(1, user.getUsername());
		query.setString(2, user.getUsername());
		query.addEntity(User.class);
		List<User> users = query.list();
		System.out.println(users);
		return users;
	}
}
