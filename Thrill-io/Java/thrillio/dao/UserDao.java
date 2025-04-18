package thrillio.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.PreparedStatement;

import thrillio.DataStore;
import thrillio.constants.Gender;
import thrillio.constants.UserType;
import thrillio.entities.User;
import thrillio.managers.UserManager;

public class UserDao {

    private static final String DB_URL = "jdbc:mysql://mysql:3306/jid_thrillio?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public List<User> getUsers() {
        return DataStore.getUsers();
    }

    public User getUser(long userId) {
        User user = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {

            PreparedStatement pstmt = null;

            String query = "Select id from User where email = ? and password = ?";
            System.out.println("query: " + query);

            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getLong("id"));
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public long checkForExistingUser(String email) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {

            PreparedStatement checkForExistingPstmt = null;

            String checkForExistingAccountQuery = "Select id from User where email = ?";

            checkForExistingPstmt = conn.prepareStatement(checkForExistingAccountQuery);

            checkForExistingPstmt.setString(1, email);

            ResultSet rs = checkForExistingPstmt.executeQuery();

            // User exists
            while (rs.next()) {
                return rs.getLong("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // User does not exist
        return -1;

    }

    public long newSignUp(String email, String password, String firstName, String lastName, String gender) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        long userId = checkForExistingUser(email);
        System.out.println("userId:" + userId);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {

            PreparedStatement pstmt = null;

            // User does not exist
            if (userId == -1) {
                String query = "Insert into User (email, password, first_name, last_name, gender_id, user_type_id, created_date) "
                        + " values(?, ?, ?, ?, ?, ?, ?)";

                pstmt = conn.prepareStatement(query);

                pstmt.setString(1, email);
                pstmt.setString(2, password);
                pstmt.setString(3, firstName);
                pstmt.setString(4, lastName);
                pstmt.setString(7, dtf.format(now));

                if (gender.equalsIgnoreCase("Male")) {

                    pstmt.setInt(5, Gender.MALE.ordinal());
                    pstmt.setNull(6, java.sql.Types.TINYINT);

                    int rs = pstmt.executeUpdate();
                    System.out.println("Query: " + query);

                    return rs;
                }

                else if (gender.equalsIgnoreCase("Female")) {

                    pstmt.setInt(5, Gender.FEMALE.ordinal());
                    pstmt.setNull(6, java.sql.Types.TINYINT);

                    int rs = pstmt.executeUpdate();
                    System.out.println("Query: " + query);

                    return rs;
                }

                else if (gender.equalsIgnoreCase("Other")) {

                    pstmt.setInt(5, Gender.OTHER.ordinal());
                    pstmt.setNull(6, java.sql.Types.TINYINT);

                    int rs = pstmt.executeUpdate();
                    System.out.println("Query: " + query);

                    return rs;
                }

                else {

                    pstmt.setNull(5, java.sql.Types.TINYINT);
                    pstmt.setNull(6, java.sql.Types.TINYINT);

                    int rs = pstmt.executeUpdate();
                    System.out.println("Query: " + query);

                    return rs;
                }
            }

            else {
                System.out.println("The email already exists!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
