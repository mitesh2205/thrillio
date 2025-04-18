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
		User user = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false", "root", "root12345");
				Statement stmt = conn.createStatement();) {	
			String query = "Select * from User where id = " + userId;
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int gender_id = rs.getInt("gender_id");
				Gender gender = Gender.values()[gender_id];
				int user_type_id = rs.getInt("user_type_id");
				UserType userType = UserType.values()[user_type_id];
				
				user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return user;
		
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
