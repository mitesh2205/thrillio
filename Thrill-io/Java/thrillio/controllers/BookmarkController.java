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

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
                long newBookId = -1;
                String errorMessage = null;
                
                Document doc = null;
                String title = "";
                String goodreadsRating = "";
                String publisherName = "";
                String imageUrl = "";
                String authorName = "";
                int publicationYear = 0;

                try {
                    System.out.println("Fetching URL: " + bookUrl);
                    doc = Jsoup.connect(bookUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                        .timeout(10000)
                        .get();
                    
                    // Debug: Print the raw HTML
                    System.out.println("HTML Title found: " + doc.title());
                    
                    // Updated selectors for new Goodreads structure
                    title = "";
                    // Try multiple selectors for title
                    Elements titleElements = doc.select("h1.Text__title1");
                    if (titleElements.isEmpty()) {
                        titleElements = doc.select("h1[data-testid='bookTitle']");
                    }
                    if (titleElements.isEmpty()) {
                        titleElements = doc.select("h1");
                    }
                    if (!titleElements.isEmpty()) {
                        title = titleElements.first().text().trim();
                    }
                    
                    // Author name
                    authorName = "";
                    Elements authorElements = doc.select("span.ContributorLink__name");
                    if (authorElements.isEmpty()) {
                        authorElements = doc.select("a.ContributorLink");
                    }
                    if (authorElements.isEmpty()) {
                        authorElements = doc.select(".authorName__container");
                    }
                    if (!authorElements.isEmpty()) {
                        authorName = authorElements.first().text().trim();
                    }
                    
                    // Rating
                    goodreadsRating = "0.0";
                    Elements ratingElements = doc.select("div.RatingStatistics__rating");
                    if (ratingElements.isEmpty()) {
                        ratingElements = doc.select("div[data-testid='ratingStats'] span");
                    }
                    if (ratingElements.isEmpty()) {
                        ratingElements = doc.select("span[itemprop='ratingValue']");
                    }
                    if (!ratingElements.isEmpty()) {
                        String ratingText = ratingElements.first().text().trim();
                        try {
                            goodreadsRating = ratingText.replaceAll("[^0-9.]", "");
                        } catch (Exception e) {
                            System.out.println("Error parsing rating: " + e.getMessage());
                        }
                    }
                    
                    // Image URL
                    imageUrl = "";
                    Elements imageElements = doc.select("img.ResponsiveImage");
                    if (imageElements.isEmpty()) {
                        imageElements = doc.select("div.bookCoverPrimary img");
                    }
                    if (imageElements.isEmpty()) {
                        imageElements = doc.select(".editionCover img");
                    }
                    if (!imageElements.isEmpty()) {
                        imageUrl = imageElements.first().attr("src");
                        if (!imageUrl.startsWith("http")) {
                            imageUrl = "https:" + imageUrl;
                        }
                    }
                    
                    // Publisher and Publication Year (more complex extraction)
                    publisherName = "";
                    publicationYear = 0;
                    
                    // Try to extract publication details from the page
                    Elements detailsElements = doc.select("div.DetailsLayoutRightParagraph");
                    if (!detailsElements.isEmpty()) {
                        for (Element details : detailsElements) {
                            String text = details.text();
                            System.out.println("Details element text: " + text);
                            
                            // Extract year
                            Pattern yearPattern = Pattern.compile("\\b(19|20)\\d{2}\\b");
                            Matcher yearMatcher = yearPattern.matcher(text);
                            if (yearMatcher.find()) {
                                try {
                                    publicationYear = Integer.parseInt(yearMatcher.group(0));
                                    System.out.println("Found publication year: " + publicationYear);
                                } catch (NumberFormatException e) {
                                    System.out.println("Error parsing year: " + e.getMessage());
                                }
                            }
                            
                            // Extract publisher
                            if (text.contains("by")) {
                                int byIndex = text.indexOf("by");
                                if (byIndex != -1) {
                                    String potentialPublisher = text.substring(byIndex + 2).trim();
                                    if (potentialPublisher.contains("(")) {
                                        potentialPublisher = potentialPublisher.substring(0, potentialPublisher.indexOf("(")).trim();
                                    }
                                    publisherName = potentialPublisher;
                                    System.out.println("Found publisher: " + publisherName);
                                }
                            }
                        }
                    }
                    
                    // Debug information
                    System.out.println("Extracted data:");
                    System.out.println("Title: " + title);
                    System.out.println("Author: " + authorName);
                    System.out.println("Rating: " + goodreadsRating);
                    System.out.println("Image URL: " + imageUrl);
                    System.out.println("Publisher: " + publisherName);
                    System.out.println("Year: " + publicationYear);
                    
                    if (StringUtils.isBlank(title) || StringUtils.isBlank(authorName)) {
                        errorMessage = "Could not extract book details from the provided URL. Please ensure it's a valid Goodreads book URL. (Title: '" + title + "', Author: '" + authorName + "')";
                    } else {
                        try {
                            double rating = StringUtils.isBlank(goodreadsRating) ? 0.0 : Double.parseDouble(goodreadsRating);
                            newBookId = BookmarkManager.getInstance().addNewBookToDatabase(title, authorName, bookUrl, imageUrl, publisherName, 
                                    publicationYear, rating);
                            if (newBookId == -1) {
                                errorMessage = "Failed to add the book to the database. Please check server logs.";
                            }
                        } catch (NumberFormatException nfe) {
                            System.err.println("Error parsing rating: " + goodreadsRating);
                            nfe.printStackTrace();
                            errorMessage = "Could not parse rating from the provided URL.";
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Error connecting to or parsing URL: " + bookUrl);
                    e.printStackTrace();
                    errorMessage = "Error accessing the provided URL. Please check if it's correct and accessible. Error: " + e.getMessage();
                } catch (Exception e) {
                    System.err.println("Unexpected error processing book URL: " + bookUrl);
                    e.printStackTrace();
                    errorMessage = "An unexpected error occurred while adding the book: " + e.getMessage();
                }
                
                if (errorMessage != null) {
                    request.setAttribute("addBookError", errorMessage);
                } else {
                    request.setAttribute("addBookSuccess", "Book '" + title + "' added successfully!");
                }

                Collection<Bookmark> bookList = BookmarkManager.getInstance().getBooks(false, userId);
                request.setAttribute("books", bookList);
                
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