package thrillio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import thrillio.constants.BookGenre;
import thrillio.constants.Gender;
import thrillio.constants.UserType;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.entities.UserBookmark;
import thrillio.managers.BookmarkManager;
import thrillio.managers.UserManager;

public class DataStore {
    private static List<User> users = new ArrayList<>();
    private static List<List<Bookmark>> bookmarks = new ArrayList<>();
    private static List<UserBookmark> userBookmarks = new ArrayList<>();
    
    public static List<User> getUsers() {
        return users;
    }
    
    public static List<List<Bookmark>> getBookmarks() {
        return bookmarks;
    }

    public static void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false", "root", "");
                Statement stmt = conn.createStatement();) {
                loadUsers(stmt);
                loadWebLinks(stmt);
                loadBooks(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    private static void loadUsers(Statement stmt) throws SQLException {
        String query = "Select * from User";
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()) {
            long id = rs.getLong("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int gender_id = rs.getInt("gender_id");
            Gender gender = Gender.values()[gender_id];
            int user_type_id = rs.getInt("user_type_id");
            UserType userType = UserType.values()[user_type_id];
            
            User user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
            users.add(user);
        }
    }
    
    private static void loadWebLinks(Statement stmt) throws SQLException {
        String query = "Select * from WebLink";
        ResultSet rs = stmt.executeQuery(query);
        
        List<Bookmark> bookmarkList = new ArrayList<>();
        
        while(rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            String url = rs.getString("url");
            String host = rs.getString("host");
            
            Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id, title, "", url, host);
            bookmarkList.add(bookmark);
        }
        
        bookmarks.add(bookmarkList);
    }
    
    private static void loadBooks(Statement stmt) throws SQLException {
        String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
                + " from Book b, Publisher p, Author a, Book_Author ba "
                + "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
        
        ResultSet rs = stmt.executeQuery(query);
        
        List<Bookmark> bookmarkList = new ArrayList<>();
        while(rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            int publicationYear = rs.getInt("publication_year");
            String publisher = rs.getString("name");      
            String[] authors = rs.getString("authors").split(",");
            int genre_id = rs.getInt("book_genre_id");
            BookGenre genre = BookGenre.values()[genre_id];
            double amazonRating = rs.getDouble("amazon_rating");
            
            Date createdDate = rs.getDate("created_date");
            Timestamp timeStamp = rs.getTimestamp(8);
            
            Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, "", "", publicationYear, publisher, authors, genre, amazonRating);
            bookmarkList.add(bookmark);
        }
        
        bookmarks.add(bookmarkList);
    }
    
    public static void add(UserBookmark userBookmark) {
        userBookmarks.add(userBookmark);
    }
}
