package alliancebackend.dao;

import java.util.List;

import alliancebackend.model.User;

public interface UserDao {
	
		User authenticate(User user);

		List<User> getAllUsers();

		User registerUser(User user);
	 
		User updateUser(int Id, User user);
		
		void deleteUser(int id);
		
		User getUserById(int userid);

		public List<User> getAllUsers(User user);
	 
}
