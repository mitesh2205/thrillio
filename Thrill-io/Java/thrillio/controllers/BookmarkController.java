package thrillio.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Added missing imports
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import thrillio.constants.KidFriendlyStatus;
import thrillio.entities.Bookmark;
import thrillio.entities.User;
import thrillio.managers.BookmarkManager;
import thrillio.managers.UserManager;

@WebServlet(urlPatterns = {"/bookmark", "/bookmark/save", "/bookmark/mybooks", "/bookmark/remove", "/bookmark/addbook"})
public class BookmarkController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookmarkController() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        System.out.println("Servlet path: " + request.getServletPath());
        
        if(request.getSession().getAttribute("userId") != null) {
            long userId = (long) request.getSession().getAttribute("userId");
            
            if(request.getServletPath().contains("save")) {
                dispatcher = request.getRequestDispatcher("/mybooks.jsp");
                String bid = request.getParameter("bid");
                
                User user = UserManager.getInstance().getUser(userId);
                Bookmark bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bid));
                BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
                
                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(true, userId);
                request.setAttribute("books", bookList);
                request.setAttribute("user", user);
                
            } else if(request.getServletPath().contains("mybooks")) {
                dispatcher = request.getRequestDispatcher("/mybooks.jsp");
                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(true, userId);
                request.setAttribute("books", bookList);
                
                User userInfo = UserManager.getInstance().getUser(userId);
                request.setAttribute("user", userInfo);
            
            } else if(request.getServletPath().contains("remove")) {
                dispatcher = request.getRequestDispatcher("/mybooks.jsp");
                String bid = request.getParameter("bid");
                
                User user = UserManager.getInstance().getUser(userId);
                Bookmark bookmark = BookmarkManager.getInstance().getBook(Long.parseLong(bid));
                BookmarkManager.getInstance().deleteUserBookmark(user, bookmark);
                
                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(true, userId);
                request.setAttribute("books", bookList);
                request.setAttribute("user", user);
            
            } else if(request.getServletPath().contains("addbook")) {
                dispatcher = request.getRequestDispatcher("/browse.jsp");
                String bookUrl = request.getParameter("book_url");
                long newBookId = -1; // Initialize with error state
                String errorMessage = null; // To store potential error messages
                
                // Declare variables outside the try block to ensure correct scope
                Document doc = null;
                Elements titleLabel = null;
                Elements ratingValue = null;
                String publicationYearRow = "";
                Elements image = null;
                String authorNameString = "";
                String title = "";
                String goodreadsRating = "";
                String publisherName = "";
                String imageUrl = "";
                String authorName = "";

                try {
                    doc = Jsoup.connect(bookUrl).get();
                    titleLabel = doc.select("h1");
                    ratingValue = doc.select("span[itemprop=\"ratingValue\"]");
                    publicationYearRow = doc.select(".row:nth-child(2)").text();
                    image = doc.select("div.editionCover > img");
                    authorNameString = doc.select("div.authorname__container").text();
                
                    title = titleLabel.text();
                    goodreadsRating = ratingValue.text();
                    publisherName = ""; // Initialize here before conditional logic
                
                if(publicationYearRow.contains("-") && publicationYearRow.contains("(")) {
                    Pattern p = Pattern.compile("[a-zA-Z]{1,}-[a-zA-Z]{1,}");
                    Matcher m = p.matcher(publicationYearRow);
                    
                    if (m.find()) {
                        publisherName = StringUtils.substringBefore(publicationYearRow, " (");
                        publisherName = StringUtils.substringAfter(publisherName, "by ");
                    } else {
                        publisherName = StringUtils.substringBefore(publicationYearRow, " - (");
                        publisherName = StringUtils.substringAfter(publisherName, "by ");
                    }
                } else {
                    publisherName = StringUtils.substringBefore(publicationYearRow, " (");
                    publisherName = StringUtils.substringAfter(publisherName, "by ");
                }
                
                // Assign to existing variable, don't redeclare
                imageUrl = image.attr("abs:src"); 
                // Assign to existing variable, don't redeclare
                authorName = ""; 
                
                if(authorNameString.contains(",")) {
                    if(authorNameString.contains("(")) {
                        if(authorNameString.indexOf('(') < authorNameString.indexOf(',')) {
                            authorName = StringUtils.substringBefore(authorNameString, " (");
                        } else {
                            authorName = StringUtils.substringBefore(authorNameString, ",");
                        }
                    }
                } else {
                    authorName = StringUtils.substringBefore(authorNameString, " (");
                }
                    
                    // Print the value of publicationYearRow to the console (temporary)
                    System.out.println("publicationYearRow: " + publicationYearRow);

                    // Use regex to find the first 4-digit number in the publicationYearRow text
                    Pattern yearPattern = Pattern.compile("\\b(\\d{4})\\b"); // Matches a 4-digit number as a whole word
                    Matcher yearMatcher = yearPattern.matcher(publicationYearRow);
                    
                    if (yearMatcher.find()) {
                        String yearStr = yearMatcher.group(1); // Get the matched 4-digit year
                        // Check if essential data was scraped
                        if (StringUtils.isBlank(title) || StringUtils.isBlank(authorName)) {
                            errorMessage = "Could not extract book details from the provided URL. Please ensure it's a valid Goodreads book URL.";
                        } else {
                            try {
                                 // Ensure rating is parseable, handle potential NumberFormatException here too
                                double rating = StringUtils.isBlank(goodreadsRating) ? 0.0 : Double.parseDouble(goodreadsRating); 
                                newBookId = BookmarkManager.getInstance().addNewBookToDatabase(title, authorName, bookUrl, imageUrl, publisherName, 
                                        Integer.parseInt(yearStr), rating);
                                if (newBookId == -1) {
                                    errorMessage = "Failed to add the book to the database. Please check server logs.";
                                }
                            } catch (NumberFormatException nfe) {
                                System.err.println("Error parsing rating or year: " + goodreadsRating + ", " + yearStr);
                                nfe.printStackTrace();
                                errorMessage = "Could not parse rating or year from the provided URL.";
                            }
                        }
                    } else {
                        // No 4-digit year found in the string
                        errorMessage = "Could not determine the publication year from the provided URL.";
                    }

                } catch (IOException e) {
                    System.err.println("Error connecting to or parsing URL: " + bookUrl);
                    e.printStackTrace();
                    errorMessage = "Error accessing the provided URL. Please check if it's correct and accessible.";
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing rating or year from URL: " + bookUrl);
                    e.printStackTrace();
                    errorMessage = "Could not parse rating or year from the provided URL.";
                } catch (Exception e) { // Catch any other unexpected errors during scraping/processing
                     System.err.println("Unexpected error processing book URL: " + bookUrl);
                     e.printStackTrace();
                     errorMessage = "An unexpected error occurred while adding the book.";
                }
                
                // Set error attribute if something went wrong
                if (errorMessage != null) {
                    request.setAttribute("addBookError", errorMessage);
                } else {
                     request.setAttribute("addBookSuccess", "Book '" + title + "' added successfully!"); // Optional success message
                }

                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(false, userId);
                request.setAttribute("books", bookList); // Refresh book list regardless of add success/failure
                
                User userInfo = UserManager.getInstance().getUser(userId);
                request.setAttribute("user", userInfo);
            
            } else {
                dispatcher = request.getRequestDispatcher("/browse.jsp");
                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(false, userId);
                request.setAttribute("books", bookList);
                
                User user = UserManager.getInstance().getUser(userId);
                request.setAttribute("user", user);
            }
        } else {
            dispatcher = request.getRequestDispatcher("/login.jsp");
        }
        
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    public void saveUserBookmark(User user, Bookmark bookmark) {
        BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
    }

    public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) throws SQLException {
        BookmarkManager.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
    }

    public void share(User user, Bookmark bookmark) throws SQLException {
        BookmarkManager.getInstance().share(user, bookmark);
    }
}
