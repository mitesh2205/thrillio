package thrillio.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import thrillio.DataStore;
import thrillio.constants.BookGenre;
import thrillio.entities.Book;
import thrillio.entities.Bookmark;
import thrillio.entities.UserBookmark;
import thrillio.entities.WebLink;
import thrillio.managers.BookmarkManager;

public class BookmarkDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";  // Update this if your XAMPP MySQL has a password
    
    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            if (userBookmark.getBookmark() instanceof Book) {
                saveUserBook(userBookmark, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "Insert into User_Book (user_id, book_id) values (" + userBookmark.getUser().getId() + ", "
                + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    public void deleteUserBookmark(UserBookmark userBookmark) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            deleteUserBook(userBookmark, stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "Delete from User_Book where user_id = " + userBookmark.getUser().getId() + " and book_id = "
                + userBookmark.getBookmark().getId();
        stmt.executeUpdate(query);
    }

    public void updateKidFriendlyStatus(Bookmark bookmark) throws SQLException {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "Update Book set kid_friendly_status = " + kidFriendlyStatus
                    + ", kid_friendly_marked_by = " + userId + " where id = " + bookmark.getId();
            stmt.executeUpdate(query);
        }
    }

    public void sharedByInfo(Bookmark bookmark) throws SQLException {
        long userId = bookmark.getSharedBy().getId();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "Update Book set shared_by = " + userId + " where id = " + bookmark.getId();
            stmt.executeUpdate(query);
        }
    }

    public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
        Collection<Bookmark> result = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "";
            if (!isBookmarked) {
                query = "Select b.id, title, book_url, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
                        + "goodreads_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
                        + "b.id NOT IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
                        + " and u.id = ub.user_id) group by b.id";
            } else {
                query = "Select b.id, title, book_url, image_url, publication_year, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, "
                        + "goodreads_rating from Book b, Author a, Book_Author ba where b.id = ba.book_id and ba.author_id = a.id and "
                        + "b.id IN (select ub.book_id from User u, User_Book ub where u.id = " + userId
                        + " and u.id = ub.user_id) group by b.id";
            }

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String bookUrl = rs.getString("book_url");
                String imageUrl = rs.getString("image_url");
                int publicationYear = rs.getInt("publication_year");
                String[] authors = rs.getString("authors").split(",");
                int genre_id = rs.getInt("book_genre_id");
                BookGenre genre = BookGenre.values()[genre_id];
                double goodreadsRating = rs.getDouble("goodreads_rating");

                Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, bookUrl, imageUrl,
                        publicationYear, null, authors, genre, goodreadsRating);
                result.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Bookmark getBook(long bookId) {
        Book book = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "Select b.id, title, book_url, image_url, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, goodreads_rating, created_date"
                    + " from Book b, Publisher p, Author a, Book_Author ba " + "where b.id = " + bookId
                    + " and b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String bookUrl = rs.getString("book_url");
                String imageUrl = rs.getString("image_url");
                int publicationYear = rs.getInt("publication_year");
                String publisher = rs.getString("name");
                String[] authors = rs.getString("authors").split(",");
                int genre_id = rs.getInt("book_genre_id");
                BookGenre genre = BookGenre.values()[genre_id];
                double goodreadsRating = rs.getDouble("goodreads_rating");

                book = BookmarkManager.getInstance().createBook(id, title, bookUrl, imageUrl, publicationYear,
                        publisher, authors, genre, goodreadsRating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public long addNewBookToDatabase(String title, String authorName, String bookUrl, String imageUrl,
            String publisherName, int publicationYear, double goodreadsRating) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            title = title.replace("'", "''");
            authorName = authorName.replace("'", "''");
            publisherName = publisherName.replace("'", "''");

            String newBookQuery = "Insert into Book (title, book_url, image_url, publication_year, goodreads_rating, created_date) "
                    + " values('" + title + "', '" + bookUrl + "', '" + imageUrl + "', '" + publicationYear + "' , '"
                    + goodreadsRating + "' , '" + dtf.format(now) + "' " + ")";
            stmt.executeUpdate(newBookQuery);

            String newAuthorQuery = "Insert into Author (name) " + " select '" + authorName + "'"
                    + "Where not exists (select * from Author where name = " + "'" + authorName + "')";
            stmt.executeUpdate(newAuthorQuery);

            String newBookAuthorQuery = "Insert into Book_Author (book_id, author_id) "
                    + " values ((select id from Book where title = '" + title + "'),"
                    + " (select id from Author where name = '" + authorName + "'))";
            stmt.executeUpdate(newBookAuthorQuery);

            String newPublisherQuery = "Insert into Publisher (name) " + " select '" + publisherName + "'"
                    + "Where not exists (select id from Publisher where name = " + "'" + publisherName + "')";
            stmt.executeUpdate(newPublisherQuery);

            String updateBook_PublisherId = "Update Book Set publisher_id = (Select id from Publisher where name = "
                    + "'" + publisherName + "')" + " where title = " + "'" + title + "'";
            return stmt.executeUpdate(updateBook_PublisherId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<WebLink> getAllWebLinks() {
        List<WebLink> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "SELECT * FROM WebLink";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                String host = rs.getString("host");
                WebLink webLink = new WebLink();
                webLink.setId(id);
                webLink.setTitle(title);
                webLink.setUrl(url);
                webLink.setHost(host);
                result.add(webLink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<WebLink> getWebLinks(WebLink.DownloadStatus status) {
        List<WebLink> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement();) {
            String query = "SELECT * FROM WebLink WHERE download_status = " + status.ordinal();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String url = rs.getString("url");
                String host = rs.getString("host");
                WebLink webLink = new WebLink();
                webLink.setId(id);
                webLink.setTitle(title);
                webLink.setUrl(url);
                webLink.setHost(host);
                result.add(webLink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
