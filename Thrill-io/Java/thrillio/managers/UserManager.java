package thrillio.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import thrillio.constants.BookGenre;
import thrillio.constants.Gender;
import thrillio.constants.UserType;
import thrillio.dao.UserDao;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.util.StringUtil;

public class UserManager {

	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();
	
	private UserManager() {
	}

	public static UserManager getInstance() {
		return instance;
	}

	public User createUser(long id, String email, String password, String firstName, String lastName, Gender gender,
			UserType userType) {
		User user = new User();
		
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		
		return user;
	}
	
	
	public List<User> getUsers() {
		return dao.getUsers();
	}

	public User getUser(long userId) {
		// Delegate to UserDao to fetch user details
		return dao.getUser(userId);
	}

	public long authenticate(String email, String password) {
		return dao.authenticate(email, StringUtil.encodePassword(password));
	}
	
	public long newSignUp(String email, String password, String firstName, String lastName, String gender) {
		return dao.newSignUp(email, StringUtil.encodePassword(password), firstName, lastName, gender);
	}
	
	public long checkForExistingUser(String email) {
		return dao.checkForExistingUser(email);
	}
	
}
